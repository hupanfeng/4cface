package xyz.uben._4cface.domain.model;

import org.apache.ibatis.type.JdbcType;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.io.Serializable;

/**
 * Created by xiong on 2016/11/7.
 */
@TableMapperAnnotation(tableName = "tkb_toolwarehouse", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.DECIMAL)
    private Long mid;
    @FieldMapperAnnotation(dbFieldName = "url", jdbcType = JdbcType.VARCHAR)
    private String url;
    @FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
    private String name;
    public Warehouse(Long mid){
        setMid(mid);
    }
    public Warehouse(){

    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
