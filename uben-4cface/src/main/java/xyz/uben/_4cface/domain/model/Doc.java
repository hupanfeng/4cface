package xyz.uben._4cface.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.multipart.MultipartFile;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.util.Date;
import java.util.List;

/**
 * 知识文档
 * Created by pjy on 2016/11/7.
 */
@TableMapperAnnotation(tableName = "tkb_doc", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class Doc {
    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.BIGINT)
    private Long mid;
    @FieldMapperAnnotation(dbFieldName = "title", jdbcType = JdbcType.VARCHAR)
    private String title;
    @FieldMapperAnnotation(dbFieldName = "summary", jdbcType = JdbcType.VARCHAR)
    private String summary;
    @FieldMapperAnnotation(dbFieldName = "url", jdbcType = JdbcType.VARCHAR)
    private String url;
    @FieldMapperAnnotation(dbFieldName = "author", jdbcType = JdbcType.BIGINT)
    private Long author;
    @FieldMapperAnnotation(dbFieldName = "categoryLevelCode", jdbcType = JdbcType.VARCHAR)
    private String categoryLevelCode;
    @FieldMapperAnnotation(dbFieldName = "templateId", jdbcType = JdbcType.BIGINT)
    private Long templateId;
    @FieldMapperAnnotation(dbFieldName = "auditor", jdbcType = JdbcType.BIGINT)
    private Long auditor;
    @FieldMapperAnnotation(dbFieldName = "status", jdbcType = JdbcType.INTEGER)
    private Integer status;
    @FieldMapperAnnotation(dbFieldName = "createTime", jdbcType = JdbcType.TIMESTAMP)
    @JSONField(format="yyyy-MM-dd" )
    private Date createTime;
    @FieldMapperAnnotation(dbFieldName = "opinion", jdbcType = JdbcType.VARCHAR)
    private String opinion;
    @FieldMapperAnnotation(dbFieldName = "docpic", jdbcType = JdbcType.VARCHAR)
    private String docpic;
    @FieldMapperAnnotation(dbFieldName = "authorName", jdbcType = JdbcType.VARCHAR)
    private String authorName;

    private String content;//创建索引使用

    private Long categoryId;//查询使用
    private List<Long> orgIds;
    private List<String> orglevelCodes;

    private String[] orderNum;
    private String[] isPub;
    private String[] contents;

    private List<BaseDocContent> contentList;

    private List<Cases> cases;

    private MultipartFile picFile;

    private boolean isMessage;

    private Long selectUser;

    private List<TooInlformation> docTool;
    private int toolCount = 0;

    public Doc() {
    }

    public Doc(Long mid) {
        this.mid = mid;
    }

    /**
     * 主键
     *
     * @return
     */
    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    /**
     * 文档标题
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 文档摘要
     *
     * @return
     */
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 文档url，相对路径
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 作者，保存为用户id
     *
     * @return
     */
    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    /**
     * 知识类别层级码
     *
     * @return
     */
    public String getCategoryLevelCode() {
        return categoryLevelCode;
    }

    public void setCategoryLevelCode(String categoryLevelCode) {
        this.categoryLevelCode = categoryLevelCode;
    }

    /**
     * 知识模板
     *
     * @return
     */
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * 审核员id
     *
     * @return
     */
    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    /**
     * 状态，0-草稿、1-待审核、2-审核通过、3-审核不通过
     *
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
     * 内容,建索引需要
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public List<BaseDocContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<BaseDocContent> contentList) {
        this.contentList = contentList;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public List<Cases> getCases() {
        return cases;
    }

    public void setCases(List<Cases> cases) {
        this.cases = cases;
    }

    public MultipartFile getPicFile() {
        return picFile;
    }

    public void setPicFile(MultipartFile picFile) {
        this.picFile = picFile;
    }

    public String getDocpic() {
        return docpic;
    }

    public void setDocpic(String docpic) {
        this.docpic = docpic;
    }

    public boolean getIsMessage(){
        return isMessage;
    }

    public void setIsMessage(boolean message) {
        isMessage = message;
    }

    public Long getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(Long selectUser) {
        this.selectUser = selectUser;
    }

    public List<String> getOrglevelCodes() {
        return orglevelCodes;
    }

    public void setOrglevelCodes(List<String> orglevelCodes) {
        this.orglevelCodes = orglevelCodes;
    }

    public List<TooInlformation> getDocTool() {
        return docTool;
    }

    public void setDocTool(List<TooInlformation> docTool) {
        this.docTool = docTool;
    }

    public int getToolCount() {
        return toolCount;
    }

    public void setToolCount(int toolCount) {
        this.toolCount = toolCount;
    }
}
