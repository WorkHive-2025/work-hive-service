package workhive.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workhive.app.auth.entity.RolePermission;

public interface RolePermissionRepository
        extends JpaRepository<RolePermission, Long>,
        RolePermissionQueryRepository {
}
