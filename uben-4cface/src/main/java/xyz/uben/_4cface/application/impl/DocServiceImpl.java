package xyz.uben._4cface.application.impl;

import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.eventbus.EventBusService;
import xyz.uben.common.infra.eventbus.Subscriber;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.util.ConfigUtil;
import xyz.uben.common.rbac.domain.model.*;
import xyz.uben.common.rbac.infra.persistent.org.OrgRepository;
import xyz.uben.common.rbac.infra.persistent.resource.ResourceRepository;
import xyz.uben.common.rbac.infra.persistent.user.UserRepository;
import xyz.uben.common.wx.application.FanService;
import xyz.uben._4cface.application.DocService;
import xyz.uben._4cface.domain.model.BaseDocContent;
import xyz.uben._4cface.domain.model.Category;
import xyz.uben._4cface.domain.model.Doc;
import xyz.uben._4cface.domain.model.event.DocMessageEvent;
import xyz.uben._4cface.infra.persistent.category.CategoryRepository;
import xyz.uben._4cface.infra.persistent.doc.DocRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjy on 2016/11/7.
 */
@Service
public class DocServiceImpl extends Subscriber implements DocService {

    private static final String defaultAppid = ConfigUtil.getConfig("defaultAppid");
    private static final String baseurl = ConfigUtil.getConfig("baseurl");
    private static final String docTemplate = ConfigUtil.getConfig("docTemplate");

    @Autowired
    DocRepository docRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FanService fanService;
    @Autowired
    EventBusService eventBusService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    OrgRepository orgRepository;


    /**
     * 组织levelCode变更时发生
     *
     * @param orgEditEvent
     */
    @Subscribe
    @Override
    public void onOrgEdit(OrgEditEvent orgEditEvent) {
        String newLevelCode = orgEditEvent.getNewOrgLevelCode();
        //修改文档组织
        docRepository.updateDocOrgbyLevelCode(newLevelCode, orgEditEvent.getOrgId());
    }

    /**
     * 删除组织时发生
     *
     * @param orgDeleteEvent
     */
    @Subscribe
    @Override
    public void onOrgDelete(OrgDeleteEvent orgDeleteEvent) {
        Long orgId = orgDeleteEvent.getOrgId();
        String levelCode = orgDeleteEvent.getNewOrgLevelCode();
        if (!StringUtils.isBlank(levelCode)) {
            docRepository.deleteDocOrgByLevelCode(levelCode);
        }
    }

    /**
     * 添加文档
     *
     * @param doc 文档实体
     */
    @Override
    public void add(Doc doc) {
        docRepository.add(doc);
    }

    /**
     * 修改文档
     *
     * @param doc 文档实体
     */
    @Override
    public void update(Doc doc) {
        docRepository.update(doc);
    }

    /**
     * 删除文档
     *
     * @param mid 文档id
     */
    @Override
    public void delete(Long mid) {
        docRepository.delete(mid);
    }

    /**
     * 单条查询文档
     *
     * @param mid 文档id
     * @return
     */
    @Override
    public Doc query(Long mid) {
        return docRepository.query(mid);
    }

    /**
     * 分页查询文档
     *
     * @param doc  文档实体
     * @param page 分页实体
     * @return
     */
    @Override
    public List<Doc> queryByPage(Doc doc, PageParameter page) {
        return docRepository.queryByPage(doc, page);
    }

    /**
     * 新增文档内容
     *
     * @param docId    文档id
     * @param content  文档内容
     * @param orderNum 序号
     */
    @Override
    public void addDocContent(Long docId, String content, Integer orderNum, Integer ispub) {
        docRepository.addDocContent(docId, content, orderNum, ispub);
    }

    /**
     * 分页查询文档
     *
     * @param doc         文档对象
     * @param page        分页数据
     * @param sessionUser 登录用户
     * @return
     */
    @Override
    public List<Doc> queryByPage(Doc doc, PageParameter page, SessionUser sessionUser) {
        if (sessionUser != null) {
            doc.setSelectUser(sessionUser.getUserId());
            List<Resource> resourceList = resourceRepository.queryByRoleIds(sessionUser.getRoles());
            String reviewed = "/doc/reviewed";
            boolean isreviewed = false;
            for (Resource resource : resourceList) {
                if (resource.getUrl().equals(reviewed)) {
                    isreviewed = true;
                }
            }
            if (isreviewed) {
                List<String> orgLevelCodes = new ArrayList<String>();
                for (UserOrg userOrg : sessionUser.getUserOrgs()) {
                    Org org = orgRepository.query(userOrg.getOrgId());
                    orgLevelCodes.add(org.getLevelCode());
                }
                doc.setOrglevelCodes(orgLevelCodes);
            }
        }
        if (doc.getCategoryId() != null) {
            Category category = categoryRepository.query(doc.getCategoryId());
            doc.setCategoryLevelCode(category.getLevelCode());
        }
        return docRepository.queryByPage(doc, page);
    }

    /**
     * 查询文档内容
     *
     * @param docId 文档id
     * @return
     */
    @Override
    public List<BaseDocContent> queryContentById(Long docId) {
        return docRepository.queryContentById(docId);
    }

    /**
     * 删除文档内容
     *
     * @param docId 文档id
     */
    @Override
    public void deleteContentById(Long docId) {
        docRepository.deleteContentById(docId);
    }

    /**
     * 新增文档部门
     *
     * @param docId        文档id
     * @param orgLevelCode 部门层级码
     */
    @Override
    public void addDocOrg(Long docId, String orgLevelCode, Long orgId) {
        docRepository.addDocOrg(docId, orgLevelCode, orgId);
    }

    /**
     * 删除文档部门
     *
     * @param docId 文档id
     */
    @Override
    public void deleteDocOrg(Long docId) {
        docRepository.deleteDocOrg(docId);
    }


    /**
     * 根据作者id查询文档
     *
     * @param authorId 作者id
     * @return
     */
    @Override
    public List<Doc> queryDocOfAuthorByPage(String title, Long authorId, PageParameter page) {
        return docRepository.queryDocOfAuthorByPage(title, authorId, page);
    }

    /**
     * 根据组织及作者id查询文档
     *
     * @param orgLevelCodes 组织层级码集合
     * @param authorId      作者id
     * @return
     */
    @Override
    public List<Doc> queryDocInOrgByPage(String authorName, String title, List<String> orgLevelCodes, Long authorId, PageParameter page) {
        return docRepository.queryDocInOrgByPage(authorName, title, orgLevelCodes, authorId, page);
    }

    /**
     * 审核文档
     *
     * @param doc 文档对象
     */
    @Override
    public void reviewed(Doc doc) {
        docRepository.update(doc);
        if (doc.getStatus() == 2 && doc.getIsMessage()) {
            eventBusService.fireEvent(new DocMessageEvent(doc.getMid()));
        }
    }

    @Override
    public List<Doc> queryVague(String title) {
        return docRepository.queryVague(title);
    }

    /**
     * 查询所有文档
     *
     * @return
     */
    public List<Doc> queryAll() {
        return docRepository.queryAll();
    }

}
