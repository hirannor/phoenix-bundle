package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.User;
import phoenix.user.entity.UserToken;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author mate.karolyi
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {


    /**
     * Retrieves {@link UserToken} entity by UUID
     * @param token {@link UUID} token of operation e.g: SIGNUP
     * @return {@link UserToken} entity
     */
    UserToken findByToken(UUID token);

    /**
     * Retrieves {@link UserToken} entity by {@link User}
     * @param user {@link User}
     * @return {@link UserToken} entity
     */
    UserToken findByUser(User user);

    /**
     * Deletes token associated to user
     * @param token {@link UUID} token of operation e.g: SIGNUP
     * @return number of affected records
     */
    @Transactional
    Integer deleteByToken(UUID token);
}