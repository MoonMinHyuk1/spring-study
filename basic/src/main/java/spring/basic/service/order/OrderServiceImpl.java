package spring.basic.service.order;

import spring.basic.domain.Order;
import spring.basic.domain.member.Member;
import spring.basic.repository.discount.DiscountPolicy;
import spring.basic.repository.discount.FixDiscountPolicy;
import spring.basic.repository.member.MemberRepository;
import spring.basic.repository.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
