package spring.basic.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.basic.domain.Order;
import spring.basic.domain.member.Grade;
import spring.basic.domain.member.Member;
import spring.basic.service.member.MemberService;
import spring.basic.service.member.MemberServiceImpl;
import spring.basic.service.order.OrderService;
import spring.basic.service.order.OrderServiceImpl;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
