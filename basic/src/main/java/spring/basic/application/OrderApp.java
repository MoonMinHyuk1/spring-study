package spring.basic.application;

import spring.basic.domain.Order;
import spring.basic.domain.member.Grade;
import spring.basic.domain.member.Member;
import spring.basic.service.member.MemberService;
import spring.basic.service.member.MemberServiceImpl;
import spring.basic.service.order.OrderService;
import spring.basic.service.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}
