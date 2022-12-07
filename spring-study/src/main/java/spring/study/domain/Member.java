package spring.study.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String password;

    private String name;

    private String email;

    private String emailKakao;

    private String emailGoogle;

    private String emailNaver;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder
    public Member(String password, String name, String email, String emailKakao, String emailGoogle, String emailNaver, SocialType socialType) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.emailKakao = emailKakao;
        this.emailGoogle = emailGoogle;
        this.emailNaver = emailNaver;
        this.authority = Authority.ROLE_USER;
        this.socialType = socialType;
    }
}
