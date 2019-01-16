package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
