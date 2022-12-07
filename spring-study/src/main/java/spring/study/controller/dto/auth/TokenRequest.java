package spring.study.controller.dto.auth;

import lombok.Data;

@Data
public class TokenRequest {
    private String accessToken;
    private String refreshToken;
}
