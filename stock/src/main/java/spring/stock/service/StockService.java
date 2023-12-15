package spring.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.stock.domain.Stock;
import spring.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {
        // Stock 조회
        // 재고 감소
        // 갱신된 값 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

    @Transactional
    public synchronized void syncDecrease(Long id, Long quantity) {
        // 자바에서 지원하는 thread 한 개만 접근할 수 있게 하는 메서드
        // 스프링의 Transactional Annotation을 사용하면 (트랜잭션 시작) -> (메서드 호출) -> (트랜잭션 종료) 순으로 진행된다.
        // 프록시 객체가 서비스 호출을 완료하고 트랜잭션 종료를 실행하게 되는 사이에 다른 thread가 접근할 수 있기에 완전한 동기화를 할 수 없다.
        // synchronized는 한 프로세스 내에서만 보장이 된다.
        // 서버가 여러 개일 때 여러 프로세스가 공유 데이터에 접근을 할 수 있고, 결국 race condition이 일어날 수 있다.
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
