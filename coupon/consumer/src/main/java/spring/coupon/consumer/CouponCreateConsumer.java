package spring.coupon.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spring.coupon.domain.Coupon;
import spring.coupon.domain.FailedEvent;
import spring.coupon.repository.CouponRepository;
import spring.coupon.repository.FailedEventRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreateConsumer {

    private final CouponRepository couponRepository;

    private final FailedEventRepository failedEventRepository;

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        try {
            couponRepository.save(Coupon.builder().userId(userId).build());
            log.info("success to create coupon : {}", userId);
        } catch (Exception e) {
            log.error("failed to create coupon : {}", userId);
            failedEventRepository.save(FailedEvent.builder().userId(userId).build());
        }
    }
}
