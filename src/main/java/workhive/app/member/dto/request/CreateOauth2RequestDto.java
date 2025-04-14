package workhive.app.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    private Map<String, Objects> args;


}
