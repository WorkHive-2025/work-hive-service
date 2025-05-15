package workhive.app.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import workhive.app.member.entity.Member;

import java.util.Optional;

import static workhive.app.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Member> findByUsername(String username) {
        return Optional.ofNullable(
                query.selectFrom(member)
                        .where(member.username.eq(username))
                        .fetchOne()
        );
    }
}
