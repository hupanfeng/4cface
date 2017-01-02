package xyz.uben._4cface.infra.persistent.doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben.common.infra.persistent.IdGenerateStrategy;
import xyz.uben.common.rbac.domain.model.User;
import xyz.uben.common.rbac.infra.persistent.user.UserMapper;
import xyz.uben._4cface.domain.model.BaseDocContent;
import xyz.uben._4cface.domain.model.Doc;
import xyz.uben._4cface.infra.persistent.doctemplate.DocTemplateMapper;

import java.util.List;

/**
 * Created by pjy on 2016/11/7.
 */
@Repository
public class DocRepository extends BaseRepository<Doc, Long> {
    @Autowired
    DocMapper docMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 新增文档
     *
     * @param doc 文档实体
     */
    @Override
    public void add(Doc doc) {
        if (idGenerateStrategy.equals(IdGenerateStrategy.CUSTOM)) {
            doc.setMid((Long) idGenerator.getNextId());
        }
        getMapper(DocTemplateMapper.class).insert(doc);
    }

    /**
     * 修改文档
     *
     * @param doc 文档实体
     */
    @Override
    public void update(Doc doc) {
        docMapper.update(doc);
    }

    /**
     * 删除文档
     *
     * @param mid 文档id
     */
    @Override
    public void delete(Long mid) {
        docMapper.delete(new Doc(mid));
        deleteContentById(mid);
    }

    /**
     * 单条查询文档
     *
     * @param mid 文档id
     * @return
     */
    @Override
    public Doc query(Long mid) {
        Doc doc = docMapper.select(new Doc(mid));
        if (null != doc) {
            List<BaseDocContent> contents = docMapper.queryContentById(mid);
            if (contents != null) {
                StringBuilder sb = new StringBuilder();
                contents.forEach(content -> sb.append(content.getContent()));
                doc.setContent(sb.toString());
            }
        }
        return doc;
    }

    /**
     * 分页查询文档
     *
     * @param doc  查询实体
     * @param page 分页实体
     * @return
     */
    @Override
    public List<Doc> queryByPage(Doc doc, PageParameter page) {
        return docMapper.queryByPage(doc, page);
    }

    /**
     * 添加文档内容
     *
     * @param docId    文档id
     * @param content  内容
     * @param orderNum 序号
     * @param ispub    是否公有
     */
    public void addDocContent(Long docId, String content, Integer orderNum, Integer ispub) {
        docMapper.addDocContent(docId, content, orderNum, ispub);
    }

    /**
     * 查询文档内容
     *
     * @param docId 文档id
     * @return
     */
    public List<BaseDocContent> queryContentById(Long docId) {
        return docMapper.queryContentById(docId);
    }

    /**
     * 删除文档内容
     *
     * @param docId 文档id
     */
    public void deleteContentById(Long docId) {
        docMapper.deleteContentById(docId);
    }

    /**
     * 添加文档部门
     *
     * @param docId        文档id
     * @param orgLevelCode 部门层级码
     */
    public void addDocOrg(Long docId, String orgLevelCode, Long orgId) {
        docMapper.addDocOrg(docId, orgLevelCode, orgId);
    }

    /**
     * 删除文档部门
     *
     * @param docId 文档id
     */
    public void deleteDocOrg(Long docId) {
        docMapper.deleteDocOrg(docId);
    }

    /**
     * 根据作者id查询文档
     *
     * @param authorId 作者id
     * @return
     */
    public List<Doc> queryDocOfAuthorByPage(String title, Long authorId, PageParameter page) {
        List<Doc> docList = docMapper.queryDocOfAuthorByPage(title, authorId, page);
        for (Doc doc : docList) {
            User user = userMapper.select(new User(doc.getAuthor()));
            doc.setAuthorName(user.getName());
        }
        return docList;
    }

    /**
     * 根据组织及作者查询文档
     *
     * @param orgLevelCodes 组织层级码
     * @param authorId      作者id
     * @return
     */
    public List<Doc> queryDocInOrgByPage(String authorName, String title, List<String> orgLevelCodes, Long authorId, PageParameter page) {
        List<Doc> docList = docMapper.queryDocInOrgByPage(authorName, title, orgLevelCodes, authorId, page);
        for (Doc doc : docList) {
            User user = userMapper.select(new User(doc.getAuthor()));
            doc.setAuthorName(user.getName());
        }
        return docList;
    }

    /**
     * 根据标题模糊查询
     *
     * @param title
     * @return
     */
    public List<Doc> queryVague(String title) {
        return docMapper.queryVague(title);
    }

    /**
     * 修改文档组织表中的组织层级码
     *
     * @param levelCode 新层级码
     * @param orgId     组织id
     */
    public void updateDocOrgbyLevelCode(String levelCode, Long orgId) {
        docMapper.updateDocOrgbyLevelCode(levelCode, orgId);
    }

    /**
     * 删除文档组织表，根据组织levelCode
     *
     * @param levelCode 组织层加码
     */
    public void deleteDocOrgByLevelCode(String levelCode) {
        docMapper.deleteDocOrgByLevelCode(levelCode);
    }

    /**
     * 查询所有文档
     *
     * @return
     */
    public List<Doc> queryAll() {
        return docMapper.queryAll();
    }
}

