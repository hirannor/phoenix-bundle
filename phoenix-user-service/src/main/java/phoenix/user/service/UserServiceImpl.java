package phoenix.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.security.entity.UserPrincipal;
import phoenix.security.repository.AuthenticationCredentialsRepository;
import phoenix.user.dto.User;
import phoenix.user.entity.UserEntity;
import phoenix.user.exception.UsernameAlreadyExistException;
import phoenix.user.repository.UserRepository;

/**
 * Service implementation of {@link UserService}
 * @author mate.karolyi
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AuthenticationCredentialsRepository authenticationCredentialsRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelmapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelmapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return modelMapper.map(userRepository.findByUserName(userName), User.class);
    }

    @Override
    public void addUser(User user) throws UsernameAlreadyExistException {

        if (authenticationCredentialsRepository.findByUserName(user.getUserName()) != null) {
            throw new UsernameAlreadyExistException("Username already exist!");
        }

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userPrincipal.setUserName(user.getUserName());

        authenticationCredentialsRepository.save(userPrincipal);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userRepository.save(userEntity);
    }
}