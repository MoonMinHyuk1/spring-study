package spring.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.study.domain.Hello;

public interface HelloRepository extends JpaRepository<Hello, Long> {
    Hello findByName(String name);
}
