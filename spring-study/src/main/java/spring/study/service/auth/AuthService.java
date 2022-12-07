package spring.study.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.study.config.security.config.SecurityUtil;
import spring.study.config.security.jwt.TokenDto;
import spring.study.config.security.jwt.TokenProvider;
import spring.study.controller.dto.auth.MemberSignInRequest;
import spring.study.controller.dto.auth.MemberSignUpRequest;
import spring.study.controller.dto.auth.TokenRequest;
import spring.study.domain.Member;
import spring.study.repository.member.MemberRepository;
import spring.study.repository.redis.RedisRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisRepository redisRepository;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //회원가입
    public void signUp(MemberSignUpRequest memberSignUpRequest) {
        Member member = Member.builder()
                .email(memberSignUpRequest.getEmail())
                .name(memberSignUpRequest.getName())
                .password(passwordEncoder.encode(memberSignUpRequest.getPassword()))
                .build();
        memberRepository.save(member);
    }

    //로그인
    //1. Login Id/PW 를 기반으로 AuthenticationToken 생성 -> authenticateMember
    //2. 실제 검증, authenticate 메서드가 실행될 때 CustomUserDetailsService 의 loadUserbyUsername 실행 -> authenticateMember
    //3. 인증 정보를 기반으로 jwt 토큰 생성 후, RefreshToken 저장 -> generateToken
    public TokenDto signIn(MemberSignInRequest memberSignInRequest) {
        TokenDto tokenDto = authenticateMember(memberSignInRequest.getEmail(), memberSignInRequest.getPassword());

        return tokenDto;
    }

    //사용자 검증
    public TokenDto authenticateMember(String principal, String credentials) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, credentials);

        return generateToken(authenticationManagerBuilder.getObject().authenticate(authenticationToken));
    }

    //토큰 생성 후, redis 저장
    public TokenDto generateToken(Authentication authentication) {
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        redisRepository.setValues(authentication.getName(), tokenDto.getRefreshToken());

        return tokenDto;
    }

    //토큰 재발급
    public TokenDto refresh(TokenRequest tokenRequest) {
        if(!tokenProvider.validateToken(tokenRequest.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(tokenRequest.getAccessToken());
        String refreshToken = Optional.ofNullable(redisRepository.getValues(authentication.getName()))
                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자입니다."));
        if(!refreshToken.equals(tokenRequest.getRefreshToken())) {
            throw new RuntimeException("토큰의 사용자 정보가 일치하지 않습니다.");
        }

        return generateToken(authentication);
    }

    //로그아웃
    public void logout() {
        redisRepository.deleteValues(SecurityUtil.getCurrentMemberId());
    }
}
