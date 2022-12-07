package spring.basic.repository.discount;

import spring.basic.domain.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
