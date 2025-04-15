package workhive.app.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workhive.app.member.entity.Member;

public interface MemberRepository
        extends JpaRepository<Member, Long>, MemberQueryRepository {

    boolean existsByUsername(String username);
}
