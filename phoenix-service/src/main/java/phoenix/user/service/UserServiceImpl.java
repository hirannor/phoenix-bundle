package phoenix.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.role.entity.Role;
import phoenix.role.entity.RoleType;
import phoenix.user.entity.User;
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
    public phoenix.user.dto.User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return modelMapper.map(userRepository.findByUserName(userName), phoenix.user.dto.User.class);
    }

    @Override
    public void addUser(phoenix.user.dto.User user)  {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new UserAlreadyExistException("User already exist!");
        }

        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role =  new Role();
        role.setRoleType(RoleType.ROLE_USER);
        userEntity.setRole(role);


        userRepository.save(userEntity);
    }

    @Override
    public List<phoenix.user.dto.User> getUsers() {
        List<phoenix.user.dto.User> users = new ArrayList<phoenix.user.dto.User>(0);
        List<User> userEntites = userRepository.findAll();

        for (User userEntity : userEntites) {
            phoenix.user.dto.User user = new phoenix.user.dto.User();
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
    public void updateUser(String userName, phoenix.user.dto.User user) {
        if(userRepository.findByUserName(userName) == null) {
            throw new UserNotFoundException("User with the given id is not found");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);

        userRepository.save(userEntity);
    }
}