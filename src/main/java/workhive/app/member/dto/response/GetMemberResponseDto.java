package workhive.app.member.dto.response;

import lombok.*;
import workhive.app.member.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMemberResponseDto {

    private Long memberId;

    private String username;

    private String name;

    private Boolean isActive;

    public static GetMemberResponseDto fromEntity(Member member) {
        return GetMemberResponseDto.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .isActive(member.getIsActive())
                .build();
    }
}
