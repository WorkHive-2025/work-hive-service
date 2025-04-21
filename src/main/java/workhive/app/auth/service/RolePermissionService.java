package workhive.app.auth.service;

import workhive.app.auth.vo.PermissionVo;

import java.util.List;

public interface RolePermissionService {

    void saveAllRolePermissionsToRedisFromDB();

    List<PermissionVo> getPermissionsByRoleCodeInRedis(String roleCode);

    List<PermissionVo> getPermissionsByRoleCodeInDB(String roleCode);
}
