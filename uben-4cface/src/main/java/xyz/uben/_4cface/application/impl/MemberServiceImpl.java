package xyz.uben._4cface.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.uben._4cface.application.MemberService;
import xyz.uben._4cface.domain.model.Member;
import xyz.uben._4cface.infra.persistent.member.MemberRepository;
import xyz.uben.common.infra.mybatis.page.PageParameter;

import java.util.List;

/**
 * @author hupanfeng
 * @since 17/1/7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void add(Member member) {
        memberRepository.add(member);
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    public void delete(Long mid) {
        memberRepository.delete(mid);
    }

    @Override
    public Member query(Long mid) {
        return memberRepository.query(mid);
    }

    @Override
    public List<Member> queryByPage(Member member, PageParameter pageParameter) {
        return memberRepository.queryByPage(member, pageParameter);
    }
}
