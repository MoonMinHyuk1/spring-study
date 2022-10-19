package spring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import spring.study.domain.Hello;
import spring.study.repository.HelloRepository;

@SpringBootTest
@WebAppConfiguration
@Transactional
class StudyApplicationTests {
	@Autowired
	private HelloRepository helloRepository;

	@Test
	public void findByName() {
		helloRepository.save(new Hello("test"));
		Hello hello = helloRepository.findByName("test");
		Assertions.assertThat(hello.getName()).isEqualTo("test");
	}

}
