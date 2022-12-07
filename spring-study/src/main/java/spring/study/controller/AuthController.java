package spring.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.study.config.security.jwt.TokenDto;
import spring.study.controller.dto.auth.MemberSignInRequest;
import spring.study.controller.dto.auth.MemberSignUpRequest;
import spring.study.controller.dto.auth.RefreshTokenResponse;
import spring.study.controller.dto.auth.TokenRequest;
import spring.study.response.BaseResponse;
import spring.study.service.auth.AuthService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        authService.signUp(memberSignUpRequest);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder().message("회원가입 되었습니다.").build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody MemberSignInRequest memberSignInRequest, HttpServletResponse response) {
        TokenDto tokenDto = authService.signIn(memberSignInRequest);
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<RefreshTokenResponse>builder().message("로그인 되었습니다.")
                .data(RefreshTokenResponse.builder().refreshToken(tokenDto.getRefreshToken()).build()).build());
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody TokenRequest tokenRequest, HttpServletResponse response) {
        TokenDto tokenDto = authService.refresh(tokenRequest);
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<RefreshTokenResponse>builder().message("토큰 재발급 되었습니다.")
                .data(RefreshTokenResponse.builder().refreshToken(tokenDto.getRefreshToken()).build()).build());
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        authService.logout();

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder().message("로그아웃 되었습니다."));
    }
}
