package xyz.uben._4cface.infra.persistent.doc;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben._4cface.domain.model.BaseDocContent;
import xyz.uben._4cface.domain.model.Doc;

import java.util.List;

/**
 * Created by pjy on 2016/11/7.
 */
@SimpleMapper
public interface DocMapper extends BaseMapper<Doc> {
    /**
     * 新增文档内容
     *
     * @param docId    文档id
     * @param content  文档内容
     * @param orderNum 序号
     */
    void addDocContent(@Param("docId") Long docId, @Param("content") String content, @Param("orderNum") Integer orderNum, @Param("ispub") Integer ispub);

    /**
     * 查询文档内容
     *
     * @param docId 文档id
     * @return
     */
    List<BaseDocContent> queryContentById(@Param("docId") Long docId);

    /**
     * 删除文档内容
     *
     * @param docId 文档id
     */
    void deleteContentById(@Param("docId") Long docId);

    /**
     * 新增文档部门
     */
    void addDocOrg(@Param("docId") Long docId, @Param("orgLevelCode") String orgLevelCode, @Param("orgId") Long orgId);

    /**
     * 删除文档部门
     *
     * @param docId 文档id
     */
    void deleteDocOrg(@Param("docId") Long docId);

    /**
     * 根据作者分页查询文档
     *
     * @param authorId 作者id
     * @return
     */
    List<Doc> queryDocOfAuthorByPage(@Param("title") String title, @Param("authorId") Long authorId, @Param("page") PageParameter page);


    /**
     * 根据组织及作者查询文档
     *
     * @param orgLevelCodes 组织层级码
     * @param authorId      作者id
     * @return
     */
    List<Doc> queryDocInOrgByPage(@Param("authorName") String authorName, @Param("title") String title, @Param("orgLevelCodes") List<String> orgLevelCodes, @Param("authorId") Long authorId, @Param("page") PageParameter page);

    /**
     * 跟据标题模糊查询
     *
     * @return
     */
    public List<Doc> queryVague(@Param("title") String title);

    /**
     * 修改文档组织表中的组织层级码
     *
     * @param levelCode 新层级码
     * @param orgId     组织id
     */
    void updateDocOrgbyLevelCode(@Param("levelCode") String levelCode, @Param("orgId") Long orgId);

    /**
     * 删除文档组织
     *
     * @param levelCode 组织levelCode
     */
    void deleteDocOrgByLevelCode(@Param("levelCode") String levelCode);

    /**
     * 查询所有文档
     *
     * @return
     */
    List<Doc> queryAll();
}
