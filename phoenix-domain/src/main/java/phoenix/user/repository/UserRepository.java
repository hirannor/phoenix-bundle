package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.UserProfileEntity;

import javax.transaction.Transactional;

/**
 * User related repository used for CRUD operations
 * @author mate.karolyi
 */
@Repository
public interface UserRepository extends JpaRepository<UserProfileEntity, Long> {

    /**
     * @param userName
     * @return
     */
    UserProfileEntity findByUserName(String userName);

    /**
     * @param userName
     */
    @Transactional
    void deleteByUserName(String userName);
}
