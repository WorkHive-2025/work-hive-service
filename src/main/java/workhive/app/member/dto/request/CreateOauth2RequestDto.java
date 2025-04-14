package workhive.app.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import workhive.app.member.entity.Member;
import workhive.app.member.entity.Oauth2;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOauth2RequestDto {

    @NotBlank(message = "소셜 로그인 실패\n 필수 정보가 누락되었습니다.")
    private String platform;

    @NotBlank(message = "소셜 로그인 실패\n 필수 정보가 누락되었습니다.")
    private String key;

    private Map<String, Object> args;

    public Oauth2 toEntity(Member member) {
        return Oauth2.builder()
                .platform(platform)
                .key(key)
                .args(args)
                .member(member)
                .build();
    }
}
