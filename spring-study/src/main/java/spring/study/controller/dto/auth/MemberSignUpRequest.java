package spring.study.controller.dto.auth;

import lombok.Data;

@Data
public class MemberSignUpRequest {
    private String email;
    private String name;
    private String password;
}
