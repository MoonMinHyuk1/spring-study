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
}
