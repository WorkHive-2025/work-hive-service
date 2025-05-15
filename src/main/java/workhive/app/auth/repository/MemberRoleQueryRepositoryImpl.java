package workhive.app.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import workhive.app.auth.entity.MemberRole;

import java.util.List;

import static workhive.app.auth.entity.QMemberRole.memberRole;
import static workhive.app.auth.entity.QRole.role;
import static workhive.app.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRoleQueryRepositoryImpl implements MemberRoleQueryRepository{

    private final JPAQueryFactory query;


    @Override
    public List<MemberRole> findByMemberId(Long memberId) {
        return query.selectFrom(memberRole)
                .join(memberRole.role, role).fetchJoin()
                .join(memberRole.member, member).fetchJoin()
                .where(memberRole.member.id.eq(memberId))
                .fetch();
    }
}
