package spring.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hello {
    @Id @GeneratedValue
    private Long id;
    private String name;

    public Hello(String name) {
        this.name = name;
    }
}
