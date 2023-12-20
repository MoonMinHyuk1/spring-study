package spring.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
