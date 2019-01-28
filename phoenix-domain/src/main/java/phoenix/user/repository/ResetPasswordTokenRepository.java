package phoenix.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.user.entity.ResetPasswordToken;
import phoenix.user.entity.User;

@Repository
public interface ResetPasswordTokenRepository  extends JpaRepository<ResetPasswordToken, Long> {

    ResetPasswordToken findByToken(String token);

    ResetPasswordToken findByUser(User user);
}