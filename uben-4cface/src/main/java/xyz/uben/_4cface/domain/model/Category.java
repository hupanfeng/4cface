package xyz.uben._4cface.domain.model;

import org.apache.ibatis.type.JdbcType;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@TableMapperAnnotation(tableName = "tkb_category", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.DECIMAL)
    private Long mid;
    @FieldMapperAnnotation(dbFieldName = "categoryName", jdbcType = JdbcType.VARCHAR)
    private String categoryName;
    @FieldMapperAnnotation(dbFieldName = "pid", jdbcType = JdbcType.DECIMAL)
    private Long pid;
    @FieldMapperAnnotation(dbFieldName = "orderNum", jdbcType = JdbcType.DECIMAL)
    private Integer orderNum;
    @FieldMapperAnnotation(dbFieldName = "levelCode", jdbcType = JdbcType.VARCHAR)
    private String levelCode;
    @FieldMapperAnnotation(dbFieldName = "createTime", jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;
    @FieldMapperAnnotation(dbFieldName = "createUserId", jdbcType = JdbcType.DECIMAL)
    private Long createUserId;
    public Long _parentId;//为了能够显示树形结构

    private String pLevelCode;

    /**
     * 用户所属角色
     */
    private List<Long> roles;
    /**
     * 用户所属组织
     */
    private List<Long> orgIds;

    private List<Category> subCategorys;

    /**
     * 层级码生成方法:父层级码 + mid + “-”
     */
    public Category generateLevelCode() {
        if (null != pLevelCode) {
            levelCode = pLevelCode + mid + "-";
        } else {
            levelCode = mid + "-";
        }
        return this;
    }

    public Category(Long mid) {
        setMid(mid);
    }

    public Category() {

    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 父节点序号
     *
     * @return
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 父节点序号
     *
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
        if(pid != null){
            _parentId = Long.valueOf(pid);
        }
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public String getpLevelCode() {
        String pLevelCode = "";
        if (levelCode != null) {
            String[] levelCodeArray = levelCode.split("-");
            for (int i = 0; i < levelCodeArray.length - 1; i++) {
                pLevelCode += levelCodeArray[i] + "-";
            }
        }
        return pLevelCode;
    }

    public void setpLevelCode(String pLevelCode) {
        this.pLevelCode = pLevelCode;
    }

    public List<Category> getSubCategorys() {
        return subCategorys;
    }

    public void setSubCategorys(List<Category> subCategorys) {
        this.subCategorys = subCategorys;
    }
}
