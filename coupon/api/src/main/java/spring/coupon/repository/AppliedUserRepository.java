package spring.coupon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppliedUserRepository {

    private final RedisTemplate<String, String> redisTemplate;

    // redis sadd 명령어(redis의 set) -> sadd key value
    public Long add(Long userId) {
        return redisTemplate
                .opsForSet()
                .add("applied_user", userId.toString());
    }
}
