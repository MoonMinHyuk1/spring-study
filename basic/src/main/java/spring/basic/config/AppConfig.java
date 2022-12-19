package spring.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basic.repository.discount.DiscountPolicy;
import spring.basic.repository.discount.FixDiscountPolicy;
import spring.basic.repository.discount.RateDiscountPolicy;
import spring.basic.repository.member.MemberRepository;
import spring.basic.repository.member.MemoryMemberRepository;
import spring.basic.service.member.MemberService;
import spring.basic.service.member.MemberServiceImpl;
import spring.basic.service.order.OrderService;
import spring.basic.service.order.OrderServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
