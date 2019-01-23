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
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * @param userName
     */
    @Transactional
    void deleteByUserName(String userName);
}
