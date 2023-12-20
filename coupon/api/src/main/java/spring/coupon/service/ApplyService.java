package spring.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.coupon.domain.Coupon;
import spring.coupon.repository.CouponRepository;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponRepository couponRepository;

    public void apply(Long userId) {
        long count = couponRepository.count();

        if(count > 100) {
            return;
        }

        couponRepository.save(Coupon.builder().userId(userId).build());
    }
}
