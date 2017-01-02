package xyz.uben._4cface.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.JdbcType;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiong on 2016/11/9.
 */
@TableMapperAnnotation(tableName = "tkb_case", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class Cases implements Serializable {
    private static final long serialVersionUID = 1L;
    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.DECIMAL)
    private Long mid;
    @FieldMapperAnnotation(dbFieldName = "title", jdbcType = JdbcType.VARCHAR)
    private String title;
    @FieldMapperAnnotation(dbFieldName = "content", jdbcType = JdbcType.LONGVARCHAR)
    private String content;
    @FieldMapperAnnotation(dbFieldName = "abstracts", jdbcType = JdbcType.VARCHAR)
    private String abstracts;
    @FieldMapperAnnotation(dbFieldName = "docId", jdbcType = JdbcType.DECIMAL)
    private Long docId;
    @FieldMapperAnnotation(dbFieldName = "createTime", jdbcType = JdbcType.TIMESTAMP)
    @JSONField(format="yyyy-MM-dd" )
    private Date createTime;
    @FieldMapperAnnotation(dbFieldName = "author", jdbcType = JdbcType.VARCHAR)
    private String author;
    @FieldMapperAnnotation(dbFieldName = "authorId", jdbcType = JdbcType.DECIMAL)
    private Long authorId;

    public Cases(Long mid) {
        setMid(mid);
    }

    public Cases() {

    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
