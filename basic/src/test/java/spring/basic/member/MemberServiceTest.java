package spring.basic.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.basic.domain.member.Grade;
import spring.basic.domain.member.Member;
import spring.basic.service.member.MemberService;
import spring.basic.service.member.MemberServiceImpl;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();
    @Test
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }

}
