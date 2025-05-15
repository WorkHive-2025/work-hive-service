package workhive.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workhive.app.auth.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleCode(String roleCode);

}
