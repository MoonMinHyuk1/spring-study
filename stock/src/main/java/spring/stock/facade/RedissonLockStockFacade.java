package spring.stock.facade;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import spring.stock.service.StockService;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    // Redisson은 Pub Sub 기반의 구현이기 때문에 레디스의 부하를 줄여준다.
    // 락을 획득할 수 없으면 대기하고, 레디스에서 락이 획득가능해지면 클라이언트에게 알린다.
    // 별도의 시간을 설정할 수 있고, 이를 통해 데드락을 방지한다.
    // 구현이 복잡하고, 별도의 라이브러리를 사용해야 한다.
    // Lettuce는 락을 획득할 수 있을 때까지 요청을 계속 보내기 때문에 레디스에 부하가 생긴다.
    public void decrease(Long id, Long quantity) {
        RLock lock = redissonClient.getLock(id.toString());
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if(!available) {
                System.out.println("lock 획득 실패");
                return;
            }
            stockService.decrease(id, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
