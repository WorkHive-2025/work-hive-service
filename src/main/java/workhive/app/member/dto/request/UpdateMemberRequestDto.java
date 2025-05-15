package workhive.app.member.dto.request;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import workhive.app.member.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberRequestDto {

    @NotNull(message = "회원 정보를 선택해주세요.")
    private Long memberId;

    private String password;

    private Boolean isActive;

    public Member update(Member member) {
        if (StringUtils.isNotBlank(password)) {
            member.setPassword(password);
        }

        if (isActive != null) {
            member.setIsActive(isActive);
        }

        return member;
    }
}
