package xyz.uben._4cface.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.JdbcType;
import xyz.uben.common.infra.mybatis.mapper.annotation.FieldMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.TableMapperAnnotation;
import xyz.uben.common.infra.mybatis.mapper.annotation.UniqueKeyType;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 */
@TableMapperAnnotation(tableName = "member", uniqueKeyType = UniqueKeyType.Single, uniqueKey = "mid")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @FieldMapperAnnotation(dbFieldName = "mid", jdbcType = JdbcType.DECIMAL)
    private Long mid;
    @FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
    private String name;
    @FieldMapperAnnotation(dbFieldName = "mobile", jdbcType = JdbcType.VARCHAR)
    private String mobile;
    @FieldMapperAnnotation(dbFieldName = "email", jdbcType = JdbcType.VARCHAR)
    private String email;
    @FieldMapperAnnotation(dbFieldName = "birthDay", jdbcType = JdbcType.DATE)
    @JSONField(format = "yyyy-MM-dd")
    private Date birthDay;
    @FieldMapperAnnotation(dbFieldName = "point", jdbcType = JdbcType.DECIMAL)
    private Integer point;
    @FieldMapperAnnotation(dbFieldName = "grade", jdbcType = JdbcType.DECIMAL)
    private Integer grade;
    @FieldMapperAnnotation(dbFieldName = "referee", jdbcType = JdbcType.VARCHAR)
    private String referee;
    @FieldMapperAnnotation(dbFieldName = "refereeId", jdbcType = JdbcType.DECIMAL)
    private Long refereeId;

    public Member(Long mid) {
        setMid(mid);
    }

    public Member() {

    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public Long getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Long refereeId) {
        this.refereeId = refereeId;
    }
}
