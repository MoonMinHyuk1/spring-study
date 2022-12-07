package spring.basic.service.member;

import spring.basic.domain.member.Member;
import spring.basic.repository.member.MemberRepository;
import spring.basic.repository.member.MemoryMemberRepository;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
