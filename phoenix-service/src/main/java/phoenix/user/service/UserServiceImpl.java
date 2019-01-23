package phoenix.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.user.dto.User;
import phoenix.user.entity.UserEntity;
import phoenix.user.exception.UserAlreadyExistException;
import phoenix.user.exception.UserNotFoundException;
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
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelmapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelmapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        return modelMapper.map(userRepository.findByUserName(userName), User.class);
    }

    @Override
    public void addUser(User user)  {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new UserAlreadyExistException("User already exist!");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setRole(ROLE_USER);


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
        userRepository.deleteByUserName(userName);
    }

    @Override
    public void updateUser(String userName, User user) {
        if(userRepository.findByUserName(userName) == null) {
            throw new UserNotFoundException("User with the given id is not found");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userRepository.save(userEntity);
    }
}