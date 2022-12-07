package spring.study.controller.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.study.domain.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String email;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getEmail());
    }
}
