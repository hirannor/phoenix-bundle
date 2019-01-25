package phoenix.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.role.RoleType;
import phoenix.role.entity.Role;
import phoenix.user.dto.User;
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

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        User user = new User();
        phoenix.user.entity.User userEntity = userRepository.findByUserName(userName);

        BeanUtils.copyProperties(userEntity, user);
        user.setRole(userEntity.getRole().getRoleType().getValue());

        return user;
    }

    @Override
    public void addUser(User user)  {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new UserAlreadyExistException("User already exist!");
        }

        phoenix.user.entity.User userEntity = new  phoenix.user.entity.User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role =  new Role();
        role.setRoleType(RoleType.ROLE_USER);
        userEntity.setRole(role);

        userRepository.save(userEntity);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>(0);
        List<phoenix.user.entity.User> userEntites = userRepository.findAll();

        for (phoenix.user.entity.User userEntity : userEntites) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            user.setRole(userEntity.getRole().getRoleType().getValue());
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
        phoenix.user.entity.User userEntity = new  phoenix.user.entity.User();
        BeanUtils.copyProperties(user, userEntity);

        Role role = new Role();
        role.setRoleType(RoleType.valueOf(user.getRole()));
        userEntity.setRole(role);

        userRepository.save(userEntity);
    }
}