package workhive.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workhive.app.auth.entity.MemberRole;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long>, MemberRoleQueryRepository {
}
