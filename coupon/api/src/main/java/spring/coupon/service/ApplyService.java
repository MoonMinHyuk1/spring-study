package spring.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.coupon.domain.Coupon;
import spring.coupon.repository.CouponCountRepository;
import spring.coupon.repository.CouponRepository;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    public void apply(Long userId) {
        long count = couponRepository.count();

        if(count > 100) {
            return;
        }

        couponRepository.save(Coupon.builder().userId(userId).build());
    }

    public void applyWithRedis(Long userId) {
        // redis incr 명령어 -> key에 대한 value를 1씩 증가시킨다.
        // redis는 싱글스레드 기반으로 동작하여 race condition을 해결할 수 있고, 성능도 빠르다.
        Long count = couponCountRepository.increment();

        if(count > 100) {
            return;
        }

        couponRepository.save(Coupon.builder().userId(userId).build());
    }
}
