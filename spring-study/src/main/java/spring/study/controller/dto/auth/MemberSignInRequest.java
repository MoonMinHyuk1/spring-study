package spring.study.controller.dto.auth;

import lombok.Data;

@Data
public class MemberSignInRequest {
    private String email;
    private String password;
}
