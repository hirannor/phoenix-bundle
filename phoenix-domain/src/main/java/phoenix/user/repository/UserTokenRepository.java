package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.User;
import phoenix.user.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByToken(String token);

    UserToken findByUser(User user);
}