package xyz.uben._4cface.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.util.ConfigUtil;
import xyz.uben.common.infra.util.GlobalConstant;
import xyz.uben.common.infra.util.UploadUtil;
import xyz.uben.common.interfaces.BaseController;
import xyz.uben.common.interfaces.annotation.Loggable;
import xyz.uben.common.rbac.application.OrgService;
import xyz.uben.common.rbac.application.ResourceService;
import xyz.uben.common.rbac.application.UserService;
import xyz.uben.common.rbac.domain.model.Org;
import xyz.uben.common.rbac.domain.model.SessionUser;
import xyz.uben.common.rbac.domain.model.User;
import xyz.uben.common.rbac.domain.model.UserOrg;
import xyz.uben._4cface.application.*;
import xyz.uben._4cface.domain.model.*;
import xyz.uben._4cface.domain.service.IndexService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pjy on 2016/11/7.
 */
@Controller
@RequestMapping(value = "/doc")
public class DocController extends BaseController {
    @Autowired
    DocService docService;
    @Autowired
    DocTemplateService docTemplateService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrgService orgService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    IndexService indexService;
    @Autowired
    UserService userService;
    @Autowired
    CasesService casesService;
    @Autowired
    ToolInformationService toolInformationService;


    private static final String imageThumbRoot = ConfigUtil.getConfig("image.thumb.root");
    private static final String imageThumbUrlRoot = ConfigUtil.getConfig("image.thumb.url.root");


    /**
     * 分页查询文档，不需要用户登录情况
     *
     * @param doc  查询实体
     * @param page 分页实体
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(Doc doc, PageParameter page) {
        Grid g = new Grid();
        try {
            page.setSort("createTime");
            page.setOrder("desc");
            List<Doc> docList;
            docList = docService.queryByPage(doc, page, null);
            //查询文档的库存数量
            for (Doc newDoc : docList) {
                List<TooInlformation> toolList = toolInformationService.queryByDoc(newDoc.getMid());
                newDoc.setToolCount(toolList.size());
                newDoc.setDocTool(toolList);
            }
            g.setRows(docList);
            g.setTotal(page.getTotalCount());
        } catch (Exception e) {
            logger.error("", e);
        }
        return g;
    }

    /**
     * 分页查询文档，需要用户登录
     *
     * @param doc  文档参数
     * @param page 分页参数
     * @return
     */
    @RequestMapping("/dataByUser")
    @ResponseBody
    public Grid dataByUser(Doc doc, PageParameter page, HttpSession session) {
        Grid g = new Grid();
        try {
            page.setSort("createTime");
            page.setOrder("desc");
            List<Doc> docList;
            SessionUser sessionUser = (SessionUser) session.getAttribute(GlobalConstant.SESSION_INFO);
            docList = docService.queryByPage(doc, page, sessionUser);
            //查询文档的库存数量
            for (Doc newDoc : docList) {
                List<TooInlformation> toolList = toolInformationService.queryByDoc(newDoc.getMid());
                newDoc.setToolCount(toolList.size());
                newDoc.setDocTool(toolList);
            }
            g.setRows(docList);
            g.setTotal(page.getTotalCount());
        } catch (Exception e) {
            logger.error("", e);
        }
        return g;
    }

    /**
     * 查询所有文档
     *
     * @return
     */
    @RequestMapping("/dataAll")
    @ResponseBody
    public Grid dataAll() {
        Grid grid = new Grid();
        try {
            List docList = docService.queryAll();
            grid.setRows(docList);
            grid.setTotal(docList.size());
        } catch (Exception e) {
            logger.error("", e);
        }
        return grid;
    }

    /**
     * 查询单条文档记录
     *
     * @param mid 文档id
     * @return
     */
    @RequestMapping("/get/{mid}")
    @ResponseBody
    public Doc get(@PathVariable("mid") Long mid, HttpSession session) {
        Doc doc = docService.query(mid);
        User user = userService.queryOne(doc.getAuthor(), true);
        List<Cases> casesList = casesService.queryByDocId(mid);
        if (!casesList.isEmpty() && casesList.size() > 0) {
            doc.setCases(casesList);
        }
        doc.setAuthorName(user.getName());
        Category category = categoryService.queryByLevelCode(doc.getCategoryLevelCode());
        if (category != null) {
            doc.setCategoryId(category.getMid());
        }
        SessionUser sessionUser = (SessionUser) session.getAttribute(GlobalConstant.SESSION_INFO);
        List<BaseDocContent> contents = docService.queryContentById(mid);
        if (sessionUser != null) {
            if (contents != null && contents.size() > 0) {
                doc.setContentList(contents);
            }
            doc.setSelectUser(sessionUser.getUserId());
        } else {
            List<BaseDocContent> pubContents = new ArrayList<BaseDocContent>();
            for (BaseDocContent baseDocContent : contents) {
                if (baseDocContent.getIspub() == 1) {
                    pubContents.add(baseDocContent);
                }
            }
            if (pubContents != null && pubContents.size() > 0) {
                doc.setContentList(pubContents);
            }
        }
        //查询文档工具信息
        List<TooInlformation> toolList = toolInformationService.queryByDoc(doc.getMid());
        if (toolList != null && toolList.size() > 0) {
            doc.setDocTool(toolList);
        }
        return doc;
    }

    /**
     * 添加文档
     *
     * @param doc 文档实体
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Loggable(name = "知识文档管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "添加文档", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message add(Doc doc, HttpServletRequest request, HttpSession session, @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
        Message m = new Message();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(GlobalConstant.SESSION_INFO);
            //上传封面图片
            if (uploadFile.getSize() > 0) {
                String filePath = imageThumbRoot + File.separator;
                String fileUrl = request.getContextPath() + imageThumbUrlRoot;
                String name = uploadFile.getOriginalFilename();
                String prefix = name.substring(name.lastIndexOf("."));
                String fileName = new Date().getTime() + prefix;
                File base = new File(filePath);
                if (!base.exists()) base.mkdirs();
                BufferedImage image = ImageIO.read(uploadFile.getInputStream());
                if (image != null) {
                    image = UploadUtil.createThumb(image, 150, 100, 0.8f);
                }
                ImageIO.write(image, "png", new File(base + File.separator + fileName));
                fileUrl = fileUrl + fileName;
                doc.setDocpic(fileUrl);
            }
            doc.setAuthor(sessionUser.getUserId());
            User user = userService.query(sessionUser.getUserId());
            if (user != null) {
                doc.setAuthorName(user.getName());
            }
            Category category = categoryService.query(doc.getCategoryId());
            if (category != null) {
                doc.setCategoryLevelCode(category.getLevelCode());
            }
            doc.setCreateTime(new Date());
            if (StringUtils.isBlank(doc.getSummary())) {
                String summary = doc.getContent().length() > 200 ? doc.getContent().substring(0, 200) : doc.getContent();
                doc.setSummary(HtmlUtils.htmlUnescape(summary));
            }
            docService.add(doc);
            String[] contents = doc.getContents();
            String[] ispub = doc.getIsPub();
            String[] orderNum = doc.getOrderNum();
            for (int i = 0; i < contents.length; i++) {
                String content = contents[i].replaceAll("<p><br/></p>", "");
                docService.addDocContent(doc.getMid(), content, Integer.parseInt(orderNum[i]), Integer.parseInt(ispub[i]));
            }
            if (sessionUser.getUserOrgs() != null && sessionUser.getUserOrgs().size() > 0) {
                for (UserOrg userOrg : sessionUser.getUserOrgs()) {
                    Org org = orgService.query(userOrg.getOrgId());
                    docService.addDocOrg(doc.getMid(), org.getLevelCode(), org.getMid());
                }
            }
            m.setMsg("添加成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("添加失败");
        }
        return m;
    }

    /**
     * 编辑文档
     *
     * @param doc 文档实体
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @Loggable(name = "知识文档管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "添加文档", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message edit(Doc doc, HttpServletRequest request, HttpSession session, @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
        Message m = new Message();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(GlobalConstant.SESSION_INFO);
            if (uploadFile.getSize() > 0) {
                Doc deldoc = docService.query(doc.getMid());
                String delfilename = "";
                if (deldoc.getDocpic() != null) {
                    delfilename = deldoc.getDocpic().substring(deldoc.getDocpic().lastIndexOf("/") + 1);
                } else {
                    String prefix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
                    delfilename = new Date().getTime() + prefix;
                }
                String filePath = imageThumbRoot + File.separator;
                File delfile = new File(filePath + delfilename);
                if (delfile.exists()) {
                    delfile.delete();
                }
                String fileUrl = request.getContextPath() + imageThumbUrlRoot;
                String name = uploadFile.getOriginalFilename();
                String prefix = name.substring(name.lastIndexOf("."));
                String fileName = new Date().getTime() + prefix;
                File base = new File(filePath);
                if (!base.exists()) base.mkdirs();
                BufferedImage image = ImageIO.read(uploadFile.getInputStream());
                if (image != null) {
                    image = UploadUtil.createThumb(image, 150, 100, 0.8f);
                }
                ImageIO.write(image, "png", new File(base + File.separator + fileName));
                fileUrl = fileUrl + fileName;
                doc.setDocpic(fileUrl);
            }
            doc.setCreateTime(new Date());
            Category category = categoryService.query(doc.getCategoryId());
            if (category != null) {
                doc.setCategoryLevelCode(category.getLevelCode());
            }
            doc.setStatus(1);
            indexService.delete(doc.getMid());//删除索引
            if (StringUtils.isBlank(doc.getSummary())) {
                String summary = doc.getContent().length() > 200 ? doc.getContent().substring(0, 200) : doc.getContent();
                doc.setSummary(HtmlUtils.htmlUnescape(summary));
            }
            docService.update(doc);
            String[] contents = doc.getContents();
            String[] ispub = doc.getIsPub();
            String[] orderNum = doc.getOrderNum();
            docService.deleteContentById(doc.getMid());//清除一下文档内容后重新添加
            for (int i = 0; i < contents.length; i++) {
                String content = contents[i].replaceAll("<p><br/></p>", "");
                docService.addDocContent(doc.getMid(), content, Integer.parseInt(orderNum[i]), Integer.parseInt(ispub[i]));
            }
            m.setMsg("编辑成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("编辑失败");
        }
        return m;
    }

    /**
     * 文档删除
     *
     * @param mid 文档主键
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Loggable(name = "知识文档管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "删除文档", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message delete(Long mid) {
        Message m = new Message();
        try {
            //删除封面图
            Doc deldoc = docService.query(mid);
            if (!StringUtils.isBlank(deldoc.getDocpic())) {
                String delfilename = deldoc.getDocpic().substring(deldoc.getDocpic().lastIndexOf("/") + 1);
                String filePath = imageThumbRoot + File.separator;
                File delfile = new File(filePath + delfilename);
                if (delfile.exists()) {
                    delfile.delete();
                }
            }
            docService.deleteContentById(mid);//删除文档内容
            docService.deleteDocOrg(mid);//删除文档部门
            indexService.delete(mid);//删除索引
            docService.delete(mid);
            m.setMsg("删除成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("删除失败");
        }
        return m;
    }

    /**
     * 文档审核
     *
     * @param doc     文档对象
     * @param session
     * @return
     */
    @RequestMapping("/reviewed")
    @ResponseBody
    @Loggable(name = "知识文档管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "审核文档", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message reviewed(Doc doc, HttpSession session) {
        Message m = new Message();
        SessionUser sessionUser = (SessionUser) session.getAttribute(GlobalConstant.SESSION_INFO);
        try {
            doc.setAuditor(sessionUser.getUserId());
            docService.reviewed(doc);
            if (doc.getStatus() == 2) {
                indexService.create(docService.query(doc.getMid()));
            }
            m.setSuccess(true);
            m.setMsg("审核成功");
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("审核失败");
        }
        return m;
    }

}
