package xyz.uben._4cface.infra.persistent.member;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben._4cface.domain.model.Member;

import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@SimpleMapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 查询所有仓库
     *
     * @return
     */
    public List<Member> queryAll();

    /**
     * 跟据名称查询id
     *
     * @param name
     * @return
     */
    public Member queryByName(@Param("name") String name);
}
