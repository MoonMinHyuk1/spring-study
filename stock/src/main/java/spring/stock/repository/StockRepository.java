package spring.stock.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.stock.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

    // Pessimistic 락은 충돌이 빈번하게 일어난다면 성능이 좋을 수 있다.
    // 락을 통해 데이터를 제어하기 때문에 데이터 정합성이 보장된다.
    // 락을 사용하기 때문에 성능 감소가 있을 수 있다.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    Stock findByIdWithPessimisticLock(@Param("id") Long id);

    // Optimistic 락은 별도의 락을 잡지 않으므로 Pessimistic 락보다 성능상 이점이 있다.
    // (버전) 업데이트가 실패했을 때 재시도 로직을 개발자가 작성해주어야 한다.
    // 충돌이 빈번하게 일어나거나 그럴 것이라고 예상된다면 성능이 좋지 않다.
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    Stock findByIdWithOptimisticLock(@Param("id") Long id);
}
