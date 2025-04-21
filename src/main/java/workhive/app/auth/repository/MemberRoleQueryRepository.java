package workhive.app.auth.repository;

import workhive.app.auth.entity.MemberRole;

import java.util.List;

public interface MemberRoleQueryRepository {

    List<MemberRole> findByMemberId(Long memberId);
}
