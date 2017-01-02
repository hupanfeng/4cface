package xyz.uben._4cface.application;

import xyz.uben.common.application.BaseService;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.rbac.domain.model.OrgDeleteEvent;
import xyz.uben.common.rbac.domain.model.OrgEditEvent;
import xyz.uben.common.rbac.domain.model.SessionUser;
import xyz.uben._4cface.domain.model.BaseDocContent;
import xyz.uben._4cface.domain.model.Doc;

import java.util.List;

/**
 * Created by pjy on 2016/11/7.
 */
public interface DocService extends BaseService<Doc, Long> {

    /**
     * 组织levelCode变更时发生
     *
     * @param
     */
    void onOrgEdit(OrgEditEvent orgEditEvent);

    /**
     * 删除组织时发生
     */
    void onOrgDelete(OrgDeleteEvent orgDeleteEvent);

    /**
     * 添加文档内容
     *
     * @param docId    文档id
     * @param content  内容
     * @param orderNum 序号
     * @param ispub    是否公共
     */
    void addDocContent(Long docId, String content, Integer orderNum, Integer ispub);

    List<Doc> queryByPage(Doc doc, PageParameter page, SessionUser sessionUser);

    /**
     * 查询文档内容
     *
     * @param docId 文档id
     * @return
     */
    List<BaseDocContent> queryContentById(Long docId);

    /**
     * 删除文档内容
     *
     * @param docId 文档id
     */
    void deleteContentById(Long docId);

    /**
     * 新增文档组织
     *
     * @param docId        文档id
     * @param orgLevelCode 组织层级码
     */
    void addDocOrg(Long docId, String orgLevelCode, Long orgId);

    /**
     * 删除文档部门
     *
     * @param docId 文档id
     */
    void deleteDocOrg(Long docId);

    /**
     * 根据作者id查询文档
     *
     * @param authorId 作者id
     * @return
     */
    List<Doc> queryDocOfAuthorByPage(String title, Long authorId, PageParameter page);

    /**
     * 根据组织及作者id查询文档
     *
     * @param orgLevelCodes 组织层级码集合
     * @param authorId      作者id
     * @return
     */
    List<Doc> queryDocInOrgByPage(String authorName, String title, List<String> orgLevelCodes, Long authorId, PageParameter page);

    /**
     * 审核文档
     *
     * @param doc 文档对象
     */
    void reviewed(Doc doc);

    /**
     * 根据标题模糊查询
     *
     * @param title
     * @return
     */
    public List<Doc> queryVague(String title);

    /**
     * 查询所有文档
     *
     * @return
     */
    List<Doc> queryAll();
}
