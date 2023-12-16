package spring.stock.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.stock.repository.RedisLockRepository;
import spring.stock.service.StockService;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;

    private final StockService stockService;

    // lettuce 방식은 구현이 간단하지만 스핀 락 방식이므로 레디스에 부하를 줄 수 있다.
    // thread sleep을 통해 락 획득 재시도 간에 텀을 주어야 한다.
    // 데이터베이스의 Named 락 방식과 유사하다. (세션 관리에 신경을 안 써도 된다.)
    // redis-cli => (setnx key value) 명령어 사용
    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }
        try {
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
