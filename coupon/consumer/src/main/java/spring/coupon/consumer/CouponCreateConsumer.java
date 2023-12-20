package spring.coupon.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CouponCreateConsumer {

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        log.info("user_id : {}", userId);
    }
}
