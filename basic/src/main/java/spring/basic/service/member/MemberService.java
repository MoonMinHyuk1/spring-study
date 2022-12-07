package spring.basic.service.member;

import spring.basic.domain.member.Member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
