package spring.basic.config;

import spring.basic.repository.discount.DiscountPolicy;
import spring.basic.repository.discount.FixDiscountPolicy;
import spring.basic.repository.discount.RateDiscountPolicy;
import spring.basic.repository.member.MemberRepository;
import spring.basic.repository.member.MemoryMemberRepository;
import spring.basic.service.member.MemberService;
import spring.basic.service.member.MemberServiceImpl;
import spring.basic.service.order.OrderService;
import spring.basic.service.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
