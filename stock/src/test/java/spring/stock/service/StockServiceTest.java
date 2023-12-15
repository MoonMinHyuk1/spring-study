package spring.stock.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.stock.domain.Stock;
import spring.stock.repository.StockRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        stockRepository.save(Stock.builder().productId(1L).quantity(100L).build());
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    public void 재고감소() {
        //given

        //when
        stockService.decrease(1L, 1L);

        //then
        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(99L);
    }

    @Test
    public void 동시에_100개의_요청() throws InterruptedException {
        //given
        int threadCount = 100;
        // 비동기로 실행하는 작업을 단순화하여 사용할 수 있게 도와주는 API
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        // 다른 쓰레드에서 수행 중인 작업이 완료될 때까지 대기할 수 있도록 도와주는 Class
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //then
        Stock stock = stockRepository.findById(1L).orElseThrow();
        // race condition이 발생해 둘 이상의 thread가 동유 데이터에 액세스해 동시에 변경을 한다.
        // 값을 갱신하기 전에 다른 thread가 공유 데이터에 접근하기에 갱신이 누락된다.
        assertThat(stock.getQuantity()).isEqualTo(0L);
    }
}