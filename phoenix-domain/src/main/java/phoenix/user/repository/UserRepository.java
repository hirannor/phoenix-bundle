package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.User;

import javax.transaction.Transactional;

/**
 * @author mate.karolyi
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by username
     * @param userName username of the user
     * @return {@link User}
     */
    User findByUserName(String userName);

    /**
     * Retrieves a user by emailaddress
     * @param emailAddress emailAddress of the user
     * @return {@link User}
     */
    User findByEmailAddress(String emailAddress);

    /**
     * Deletes a user by username
     * @param userName {@link String} username of user
     * @return number of affected records
     */
    @Transactional
    Integer deleteByUserName(String userName);
}
