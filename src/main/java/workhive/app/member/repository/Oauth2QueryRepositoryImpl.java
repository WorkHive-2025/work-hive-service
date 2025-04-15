package workhive.app.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import workhive.app.member.entity.Oauth2;

import java.util.Optional;

import static workhive.app.member.entity.QMember.member;
import static workhive.app.member.entity.QOauth2.oauth2;

@Repository
@RequiredArgsConstructor
public class Oauth2QueryRepositoryImpl implements Oauth2QueryRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<Oauth2> findByPlatformAndKey(String platform, String key) {
        return Optional.ofNullable(
                query.selectFrom(oauth2)
                        .join(oauth2.member, member).fetchJoin()
                        .where(
                                oauth2.platform.eq(platform),
                                oauth2.key.eq(key)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<Oauth2> findByMemberId(Long memberId) {
        return Optional.ofNullable(
                query.selectFrom(oauth2)
                        .join(oauth2.member, member).fetchJoin()
                        .where(
                                oauth2.member.id.eq(memberId)
                        )
                        .fetchOne()
        );
    }
}
