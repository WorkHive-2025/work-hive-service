package workhive.app.auth.vo;

import lombok.*;
import workhive.app.auth.entity.Permission;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PermissionVo {

    private String requestPath;

    private String httpMethod;

    public static PermissionVo from(Permission permission) {
        return PermissionVo.builder()
                .requestPath(permission.getRequestPath())
                .httpMethod(permission.getHttpMethod())
                .build();
    }
}
