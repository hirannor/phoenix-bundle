package phoenix.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.user.dto.User;
import phoenix.user.entity.UserPrincipalEntity;
import phoenix.user.entity.UserProfileEntity;
import phoenix.user.exception.UserAlreadyExistException;
import phoenix.user.exception.UserNotFoundException;
import phoenix.user.repository.AuthenticationCredentialsRepository;
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
        UserProfileEntity userProfileEntity = userRepository.findByUserName(userName);
        return modelMapper.map(userRepository.findByUserName(userName), User.class);
    }

    @Override
    public void addUser(User user)  {
        if (authenticationCredentialsRepository.findByUserName(user.getUserName()) != null) {
            throw new UserAlreadyExistException("User already exist!");
        }

        UserPrincipalEntity userPrincipalEntity = new UserPrincipalEntity();
        userPrincipalEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userPrincipalEntity.setUserName(user.getUserName());
        userPrincipalEntity.setRole(ROLE_USER);

        authenticationCredentialsRepository.save(userPrincipalEntity);

        UserProfileEntity UserProfileEntity = new UserProfileEntity();
        BeanUtils.copyProperties(user, UserProfileEntity);

        userRepository.save(UserProfileEntity);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>(0);
        List<UserProfileEntity> userEntites = userRepository.findAll();

        for (UserProfileEntity UserProfileEntity : userEntites) {
            User user = new User();
            modelMapper.map(UserProfileEntity, user);
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
        if( userRepository.findByUserName(userName) == null) {
            throw new UserNotFoundException("User with the given id is not found");
        }
        UserProfileEntity userProfileEntity = new UserProfileEntity();
        BeanUtils.copyProperties(user, userProfileEntity);

        userRepository.save(userProfileEntity);
    }
}