package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.UserPrincipalEntity;

import javax.transaction.Transactional;

/**
 * This Repository used for getting authentication credentials from a store.
 * @author mate.karolyi
 */
@Repository
public interface AuthenticationCredentialsRepository extends JpaRepository<UserPrincipalEntity, Long> {

    /**
     * @param userName
     * @return
     */
    UserPrincipalEntity findByUserName(@Param(value = "userName")String userName);

    /**
     * @param userName
     */
    @Transactional
    void deleteByUserName(String userName);
}
