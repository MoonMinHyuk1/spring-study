package spring.coupon.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.coupon.repository.CouponRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void 한번만응모() {
        //given

        //when
        applyService.apply(1L);
        long count = couponRepository.count();

        //then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    public void 여러명응모() throws InterruptedException {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for(int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //then
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100L);
    }

    @Test
    public void 여러명응모_레디스() throws InterruptedException {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for(int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.applyWithRedis(userId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //then
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100L);
    }

    @Test
    public void 여러명응모_카프카() throws InterruptedException {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for(int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.applyWithKafka(userId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Thread.sleep(1000);

        //then
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100L);
    }

    @Test
    public void 여러명응모_중복X() throws InterruptedException {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        //when
        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    applyService.applyNotDuplication(1L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Thread.sleep(1000);

        //then
        long count = couponRepository.count();
        assertThat(count).isEqualTo(1L);
    }
}