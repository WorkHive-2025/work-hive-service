package workhive.app.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workhive.app.member.entity.Oauth2;

public interface Oauth2Repository
        extends JpaRepository<Oauth2, Long>, Oauth2QueryRepository {

}
