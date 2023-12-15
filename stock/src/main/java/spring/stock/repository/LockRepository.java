package spring.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.stock.domain.Stock;

public interface LockRepository extends JpaRepository<Stock, Long> {

    // Named 락은 주로 분산락을 구현할 때 사용한다.
    // 타임아웃을 손쉽게 구현할 수 있다. (Facade)
    // 트랜잭션 종료 시에 락 해제, 세션 관리를 잘 해주어야 한다.
    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(@Param("key") String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(@Param("key") String key);
}
