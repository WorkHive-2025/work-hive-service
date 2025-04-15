package workhive.app.member.repository;

import workhive.app.member.entity.Oauth2;

import java.util.Optional;

public interface Oauth2QueryRepository {

    Optional<Oauth2> findByPlatformAndKey(String platform, String key);

    Optional<Oauth2> findByMemberId(Long memberId);

}
