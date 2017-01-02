package xyz.uben._4cface.domain.model;

import org.apache.ibatis.type.JdbcType;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 知识模板
 * Created by pjy on 2016/11/1.
 */
@TableMapperAnnotation(tableName = "tkb_template", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class DocTemplate implements Serializable {
    private static final long serialVersionUID = -5107564318866181415L;

    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.BIGINT)
    private Long mid;

    @FieldMapperAnnotation(dbFieldName = "templateName", jdbcType = JdbcType.VARCHAR)
    private String templateName;

    @FieldMapperAnnotation(dbFieldName = "description", jdbcType = JdbcType.VARCHAR)
    private String description;

    @FieldMapperAnnotation(dbFieldName = "content", jdbcType = JdbcType.LONGVARCHAR)
    private String content;

    @FieldMapperAnnotation(dbFieldName = "createTime", jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    @FieldMapperAnnotation(dbFieldName = "createUserId", jdbcType = JdbcType.BIGINT)
    private Long createUserId;

    //主键集合
    private List<Long> templateids;

    //类别主键
    private String categoryId;

    private List<Long> categoryIds;

    private String[] orderNum;
    private String[] isPub;
    private String[] templateContent;

    private List<BaseDocContent> contents;

    public DocTemplate() {
    }

    public DocTemplate(Long mid) {
        this.setMid(mid);
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    /**
     * 模板名称
     *
     * @return
     */
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 模板描述
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 模板内容
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建用户
     *
     * @return
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 主键集合
     *
     * @return
     */
    public List<Long> getTemplateids() {
        return templateids;
    }

    public void setTemplateids(List<Long> templateids) {
        this.templateids = templateids;
    }

    /**
     * 类别主键
     *
     * @return
     */
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 数
     *
     * @return
     */
    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String[] getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String[] orderNum) {
        this.orderNum = orderNum;
    }

    public String[] getIsPub() {
        return isPub;
    }

    public void setIsPub(String[] isPub) {
        this.isPub = isPub;
    }

    public String[] getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String[] templateContent) {
        this.templateContent = templateContent;
    }

    /**
     * 模板内容对象集合
     *
     * @return
     */
    public List<BaseDocContent> getContents() {
        return contents;
    }

    public void setContents(List<BaseDocContent> contents) {
        this.contents = contents;
    }
}
