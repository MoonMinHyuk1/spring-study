package spring.basic.repository.member;

import spring.basic.domain.member.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
