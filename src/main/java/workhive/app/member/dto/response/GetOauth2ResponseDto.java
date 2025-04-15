package workhive.app.member.dto.response;

import lombok.*;
import workhive.app.member.entity.Oauth2;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOauth2ResponseDto {

    private Long oauth2Id;

    private String platform;

    private String key;

    private Map<String, Object> args;

    public static GetOauth2ResponseDto fromEntity(Oauth2 oauth2) {
        return GetOauth2ResponseDto.builder()
                .oauth2Id(oauth2.getId())
                .platform(oauth2.getPlatform())
                .key(oauth2.getKey())
                .args(oauth2.getArgs())
                .build();
    }
}
