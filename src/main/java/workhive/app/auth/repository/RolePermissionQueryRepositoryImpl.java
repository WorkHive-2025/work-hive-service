package workhive.app.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import workhive.app.auth.entity.RolePermission;

import java.util.List;

import static workhive.app.auth.entity.QPermission.permission;
import static workhive.app.auth.entity.QRole.role;
import static workhive.app.auth.entity.QRolePermission.rolePermission;

@Repository
@RequiredArgsConstructor
public class RolePermissionQueryRepositoryImpl implements RolePermissionQueryRepository{

    private final JPAQueryFactory query;

    @Override
    public List<RolePermission> findByRoleId(Long roleId) {
        return query.selectFrom(rolePermission)
                .join(rolePermission.role, role).fetchJoin()
                .join(rolePermission.permission, permission).fetchJoin()
                .where(rolePermission.role.id.eq(roleId))
                .fetch();
    }

}
