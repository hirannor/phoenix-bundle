package phoenix.core.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.core.security.entity.UserPrincipal;

/**
 * @author mate.karolyi
 */
@Repository
public interface AuthenticationCredentialsRepository extends JpaRepository<UserPrincipal, Long> {

    UserPrincipal findByUserName(String userName);

}
