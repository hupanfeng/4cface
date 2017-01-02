package xyz.uben._4cface.domain.model;

import org.apache.ibatis.type.JdbcType;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiong on 2016/11/7.
 */
@TableMapperAnnotation(tableName = "tkb_toolinformation", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class TooInlformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.DECIMAL)
    private Long mid;
    @FieldMapperAnnotation(dbFieldName = "toolName", jdbcType = JdbcType.VARCHAR)
    private String toolName;
    @FieldMapperAnnotation(dbFieldName = "state", jdbcType = JdbcType.DECIMAL)
    private Long state;
    @FieldMapperAnnotation(dbFieldName = "price", jdbcType = JdbcType.VARCHAR)
    private String price;
    @FieldMapperAnnotation(dbFieldName = "time", jdbcType = JdbcType.TIMESTAMP)
    private Date time;
    @FieldMapperAnnotation(dbFieldName = "code", jdbcType = JdbcType.DECIMAL)
    private Long code;
    @FieldMapperAnnotation(dbFieldName = "remark", jdbcType = JdbcType.VARCHAR)
    private String remark;
    @FieldMapperAnnotation(dbFieldName = "warName", jdbcType = JdbcType.VARCHAR)
    private String warName;
    @FieldMapperAnnotation(dbFieldName = "manufactor", jdbcType = JdbcType.VARCHAR)
    private String manufactor;
    @FieldMapperAnnotation(dbFieldName = "docId", jdbcType = JdbcType.DECIMAL)
    private Long docId;
    /**
     * 添加中间表所需的id
     */
    public Long warehouseId;

    private String key;


    public TooInlformation(Long mid) {
        setMid(mid);
    }

    public TooInlformation() {

    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWarName() {
        return warName;
    }

    public void setWarName(String warName) {
        this.warName = warName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}
