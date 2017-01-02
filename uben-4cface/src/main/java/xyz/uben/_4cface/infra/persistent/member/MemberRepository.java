package xyz.uben._4cface.infra.persistent.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben._4cface.domain.model.Member;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben._4cface.domain.model.Member;

import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
@Repository
public class MemberRepository extends BaseRepository<Member, Long> {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void add(Member member) {
        memberMapper.insert(member);
    }

    @Override
    public void update(Member member) {
        memberMapper.update(member);
    }

    @Override
    public void delete(Long mid) {
        memberMapper.delete(new Member(mid));
    }

    @Override
    public Member query(Long mid) {
        return memberMapper.select(new Member(mid));
    }

    @Override
    public List<Member> queryByPage(Member member, PageParameter page) {
        return memberMapper.queryByPage(member, page);
    }

    public List<Member> queryAll() {
        return memberMapper.queryAll();
    }

    public Member queryByName(String name) {
        return memberMapper.queryByName(name);
    }
}
