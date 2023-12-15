package spring.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.stock.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
