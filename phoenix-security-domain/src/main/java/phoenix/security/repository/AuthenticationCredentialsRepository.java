package phoenix.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import phoenix.security.entity.UserPrincipal;

/**
 * This Repository used for getting authentication credentials from a store.
 * @author mate.karolyi
 */
@Repository
public interface AuthenticationCredentialsRepository extends JpaRepository<UserPrincipal, Long> {

    UserPrincipal findByUserNameOrEmailAddress(@Param(value = "userName")String userName, @Param(value = "emailAddress") String emailAddress);
}
