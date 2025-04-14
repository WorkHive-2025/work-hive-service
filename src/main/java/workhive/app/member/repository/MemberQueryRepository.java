package workhive.app.member.repository;

import workhive.app.member.entity.Member;

import java.util.Optional;

public interface MemberQueryRepository {

    Optional<Member> findByUsername(String username);
}
