package xyz.uben._4cface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.util.GlobalConstant;
import xyz.uben.common.interfaces.BaseController;
import xyz.uben.common.interfaces.annotation.Loggable;
import xyz.uben.common.rbac.domain.model.SessionUser;
import xyz.uben._4cface.application.DocTemplateService;
import xyz.uben._4cface.domain.model.DocTemplate;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * Created by pjy on 2016/11/1.
 */
@Controller
@RequestMapping(value = "/doctemplate")
public class DocTemplateController extends BaseController {
    @Autowired
    DocTemplateService docTemplateService;

    /**
     * 分页查询模板数据
     *
     * @param template
     * @param Page
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(DocTemplate template, PageParameter Page) {
        Grid g = new Grid();
        try {
            List<DocTemplate> docTemplateList = docTemplateService.queryByPage(template, Page);
            g.setRows(docTemplateList);
            g.setTotal(docTemplateList.size());
        } catch (Exception e) {
            logger.error("", e);
        }
        return g;
    }

    /**
     * 单条模板查询
     *
     * @param mid
     * @return
     */
    @RequestMapping("/{mid}")
    @ResponseBody
    public DocTemplate get(@PathVariable("mid") Long mid) {
        return docTemplateService.query(mid);
    }

    @RequestMapping("/add")
    @ResponseBody
    @Loggable(name = "知识模板管理", level = Loggable.LOG_LEVEL_MANAGE_D, prefix = "添加模板", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message add(DocTemplate docTemplate, HttpSession session) {
        Message m = new Message();
        try {
            docTemplate.setCreateTime(new Date());
            SessionUser sessionUser = (SessionUser) session.getAttribute(GlobalConstant.SESSION_INFO);
            docTemplate.setCreateUserId(sessionUser.getUserId());
            docTemplateService.add(docTemplate);
            String[] ispub= docTemplate.getIsPub();
            String[] orderNum = docTemplate.getOrderNum();
            String[] templateContent = docTemplate.getTemplateContent();
            Long mid = docTemplate.getMid();
            for (int i = 0;i < templateContent.length ; i++) {
                docTemplateService.addTemplateContent(mid,Integer.parseInt(orderNum[i]),Integer.parseInt(ispub[i]),templateContent[i]);
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
     * 删除知识模板
     *
     * @param mid 模板id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Loggable(name = "知识模板管理", level = Loggable.LOG_LEVEL_MANAGE_D, prefix = "删除模板", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message delete(Long mid) {
        Message m = new Message();
        try {
            docTemplateService.deleteTemplateContent(mid);
            docTemplateService.delete(mid);
            m.setMsg("删除成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("删除失败");
        }
        return m;
    }

    @RequestMapping("/edit")
    @ResponseBody
    @Loggable(name = "知识模板管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "修改模板", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message edit(DocTemplate docTemplate) {
        Message m = new Message();
        try {
            docTemplateService.update(docTemplate);
            String[] ispub= docTemplate.getIsPub();
            String[] orderNum = docTemplate.getOrderNum();
            String[] templateContent = docTemplate.getTemplateContent();
            Long mid = docTemplate.getMid();
            docTemplateService.deleteTemplateContent(mid);
            for (int i = 0;i < ispub.length ; i++) {
                docTemplateService.addTemplateContent(mid,Integer.parseInt(orderNum[i]),Integer.parseInt(ispub[i]),templateContent[i]);
            }
            m.setMsg("修改成功");
            m.setSuccess(true);
        }catch (Exception e){
            logger.error("",e);
            m.setMsg("修改失败");
        }
        return m;
    }
}
