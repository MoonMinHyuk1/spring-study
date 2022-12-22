package spring.basic.repository.discount;

import org.springframework.stereotype.Component;
import spring.basic.domain.member.Grade;
import spring.basic.domain.member.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10; //10% 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent /  100;
        } else {
            return 0;
        }
    }
}
