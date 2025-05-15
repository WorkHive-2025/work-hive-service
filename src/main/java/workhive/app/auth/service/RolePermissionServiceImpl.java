package workhive.app.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import workhive.app.auth.entity.Role;
import workhive.app.auth.entity.RolePermission;
import workhive.app.auth.repository.RolePermissionRepository;
import workhive.app.auth.repository.RoleRepository;
import workhive.app.auth.vo.PermissionVo;
import workhive.app.exception.GeneralException;
import workhive.app.exception.enums.ErrorCode;
import workhive.app.global.service.RedisService;
import workhive.app.global.utils.RedisKeyGenerator;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RedisService redisService;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public void saveAllRolePermissionsToRedisFromDB() {
        //1. DB에서 Role 목록을 조회
        roleRepository.findAll()
                .forEach(this::saveRolePermissionsToRedisFromDB);
    }

    @Override
    public List<PermissionVo> getPermissionsByRoleCodeInRedis(String roleCode) {
        String key = RedisKeyGenerator.getRolePrefix(roleCode);
        return redisService.getList(key)
                .stream()
                .map(o -> {
                    var map = (LinkedHashMap) o;
                    String permissionName = (String) map.get("requestPath");
                    String permissionDescription = (String) map.get("httpMethod");
                    return new PermissionVo(permissionName, permissionDescription);
                })
                .toList();
    }

    @Override
    public List<PermissionVo> getPermissionsByRoleCodeInDB(String roleCode) {
        //1. RoleCode로 Role 조회
        Role role = roleRepository.findByRoleCode(roleCode)
                .orElseThrow(() -> {
                    log.error("[RolePermissionServiceImpl.getPermissionsByRoleCodeInDB] Role not found: {}", roleCode);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "Role not found");
                });

        //2. RolePermission 목록 조회
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role.getId());

        //3. RolePermission 목록을 PermissionVo로 변환하여 반환
        return rolePermissions.stream()
                .map(RolePermission::getPermission)
                .map(PermissionVo::from)
                .peek(vo -> {
                    saveRolePermissionsToRedisFromDB(role);
                })
                .toList();
    }

    public void saveRolePermissionsToRedisFromDB(Role role) {
        String key = RedisKeyGenerator.getRolePrefix(role.getRoleCode());

        // 기존 RolePermission 캐시 삭제
        redisService.remove(key);

        List<PermissionVo> permissionVoList = rolePermissionRepository.findByRoleId(role.getId())
                .stream()
                .map(RolePermission::getPermission)
                .map(PermissionVo::from)
                .toList();

        // RolePermission 캐시 저장
        redisService.saveList(key, permissionVoList);
    }

}
