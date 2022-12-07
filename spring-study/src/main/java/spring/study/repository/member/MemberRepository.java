package spring.study.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.study.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
