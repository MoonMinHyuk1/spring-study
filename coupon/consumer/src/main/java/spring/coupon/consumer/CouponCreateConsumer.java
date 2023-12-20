package spring.coupon.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spring.coupon.domain.Coupon;
import spring.coupon.repository.CouponRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreateConsumer {

    private final CouponRepository couponRepository;

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        log.info("user_id : {}", userId);
        couponRepository.save(Coupon.builder().userId(userId).build());
    }
}
