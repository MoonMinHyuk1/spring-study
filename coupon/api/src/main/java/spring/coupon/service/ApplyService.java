package spring.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.coupon.domain.Coupon;
import spring.coupon.producer.CouponCreateProducer;
import spring.coupon.repository.CouponCountRepository;
import spring.coupon.repository.CouponRepository;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

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

        // 문제점
        // 1. 쿠폰의 개수가 많아질수록 데이터베이스에 부하를 주게 된다.
        // 2. n분에 가능한 저장 개수가 정해져 있다고 가정하면,
        //    일정 수를 초과할 시 타임아웃에 걸리게 되고 쿠폰 발금뿐만 아니라 다른 요청까지 무시될 수 있다.
        // 3. 짧은 시간 내 많은 오쳥이 들어와 DB 서버의 리소스를 많이 사용하게 되고, 부하가 발생해 서비스 지연 혹은 오류로 이어질 수 있다.
        couponRepository.save(Coupon.builder().userId(userId).build());
    }

    public void applyWithKafka(Long userId) {
        Long count = couponCountRepository.increment();

        if(count > 100) {
            return;
        }

        // db 와는 관계없이 토픽에 userId를 전송한다.
        couponCreateProducer.create(userId);
    }
}
