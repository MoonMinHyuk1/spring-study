package spring.study.service.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.study.config.security.jwt.TokenDto;
import spring.study.controller.dto.OauthToken;
import spring.study.repository.member.MemberRepository;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class KakaoEnv implements SocialEnv {
    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private String clientSecret = null;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Override
    public OauthToken getOauthToken(RestTemplate restTemplate, HttpHeaders httpHeaders, String code) {
        return null;
    }

    @Override
    public TokenDto getUserProfile(RestTemplate restTemplate, HttpHeaders httpHeaders, String token) {
        return null;
    }
}
