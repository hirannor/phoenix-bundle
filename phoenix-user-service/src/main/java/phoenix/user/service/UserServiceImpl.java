package phoenix.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.security.entity.UserPrincipal;
import phoenix.security.repository.AuthenticationCredentialsRepository;
import phoenix.user.dto.User;
import phoenix.user.entity.UserEntity;
import phoenix.user.exception.UserAlreadyExistException;
import phoenix.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation of {@link UserService}
 *
 * @author mate.karolyi
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";

    private UserRepository userRepository;
    private AuthenticationCredentialsRepository authenticationCredentialsRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationCredentialsRepository authenticationCredentialsRepository,
                           ModelMapper modelmapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authenticationCredentialsRepository = authenticationCredentialsRepository;
        this.modelMapper = modelmapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return modelMapper.map(userRepository.findByUserName(userName), User.class);
    }

    @Override
    public void addUser(User user) throws UserAlreadyExistException {

        if (authenticationCredentialsRepository.findByUserNameOrEmailAddress(user.getUserName(), user.getEmailAddress()) != null) {
            throw new UserAlreadyExistException("User already exist!");
        }

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userPrincipal.setUserName(user.getUserName());
        userPrincipal.setEmailAddress(user.getEmailAddress());

        userPrincipal.setRole(ROLE_USER);

        authenticationCredentialsRepository.save(userPrincipal);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userRepository.save(userEntity);
    }

    @Override
    public List<User> getUsers() {

        List<User> users = new ArrayList<User>(0);
        List<UserEntity> userEntites = userRepository.findAll();

        for (UserEntity userEntity : userEntites) {
            User user = new User();
            modelMapper.map(userEntity, user);
            users.add(user);
        }
        return users;
    }

    @Override
    public void deleteUser(String userName) {
        authenticationCredentialsRepository.deleteByUserName(userName);
        userRepository.deleteByUserName(userName);
    }

    @Override
    public void updateUser(String userName, User user) {
        if(authenticationCredentialsRepository.findByUserNameOrEmailAddress(userName, null) == null) {
            // throw UserNameNotFoundException
        }

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userPrincipal.setUserName(userName);
        userPrincipal.setEmailAddress(user.getEmailAddress());

        authenticationCredentialsRepository.save(userPrincipal);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setUserName(userName);

        userRepository.save(userEntity);
    }
}