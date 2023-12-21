package spring.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.coupon.domain.FailedEvent;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {
}
