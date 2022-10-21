package spring.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.study.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
