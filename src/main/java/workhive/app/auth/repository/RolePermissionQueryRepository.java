package workhive.app.auth.repository;

import workhive.app.auth.entity.RolePermission;

import java.util.List;

public interface RolePermissionQueryRepository {

    List<RolePermission> findByRoleId(Long roleId);

}
