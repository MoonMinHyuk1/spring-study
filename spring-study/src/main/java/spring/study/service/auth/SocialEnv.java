package spring.study.service.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import spring.study.config.security.jwt.TokenDto;
import spring.study.controller.dto.OauthToken;

public interface SocialEnv {
    OauthToken getOauthToken(RestTemplate restTemplate, HttpHeaders httpHeaders, String code);

    TokenDto getUserProfile(RestTemplate restTemplate, HttpHeaders httpHeaders, String token);
}
