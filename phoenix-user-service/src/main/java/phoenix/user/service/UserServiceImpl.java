package phoenix.user.service;

import org.springframework.stereotype.Service;
import phoenix.user.entity.User;
import phoenix.user.repository.UserRepository;

/**
 * Service implementation of {@link UserService}
 * @author mate.karolyi
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}