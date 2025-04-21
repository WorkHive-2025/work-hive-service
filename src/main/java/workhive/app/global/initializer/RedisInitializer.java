package workhive.app.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import workhive.app.auth.service.RolePermissionService;
import workhive.app.auth.service.RolePermissionServiceImpl;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisInitializer implements ApplicationRunner {

    private final RolePermissionService rolePermissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[RedisInitializer.run] Redis initializing start");

        // Role - Permission 매핑 정보 Redis 저장
        rolePermissionService.saveAllRolePermissionsToRedisFromDB();

        log.info("[RedisInitializer.run] Redis initializing end");
    }
}
