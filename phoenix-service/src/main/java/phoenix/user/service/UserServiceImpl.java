package phoenix.user.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.notification.service.NotificationService;
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
    private NotificationService notificationService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
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

        notificationService.sendMessage(user.getEmailAddress(), buildRegistrationNotification(user));
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
            throw new UserNotFoundException("User with the given username is not found");
        }
        phoenix.user.entity.User userEntity = new  phoenix.user.entity.User();
        BeanUtils.copyProperties(user, userEntity);

        Role role = new Role();
        role.setRoleType(RoleType.valueOf(user.getRole()));
        userEntity.setRole(role);

        userRepository.save(userEntity);
    }

    @Override
    public void resetPassword(String userName) {
        phoenix.user.entity.User userEntity = userRepository.findByUserName(userName);
        if(userEntity == null) {
            throw new UserNotFoundException("User with the given username is not found");
        }
        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        String hashedPassword = bCryptPasswordEncoder.encode(generatedPassword);

        userEntity.setPassword(hashedPassword);

        userRepository.save(userEntity);

        notificationService.sendMessage(userEntity.getEmailAddress(), buildResetPasswordNotification(generatedPassword));
    }

    private String buildRegistrationNotification(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear " + user.getFirstName() + " " + user.getLastName() + " your registration was successfull! \n\n");
        sb.append("Your details: \n\n");
        sb.append("Username: " + user.getUserName() + "\n");
        sb.append("Password: " + user.getPassword() + "\n");
        sb.append("Firstname: " + user.getFirstName() + "\n");
        sb.append("Lastname: " + user.getFirstName() + "\n");
        sb.append("Age: " + user.getAge() + "\n");
        sb.append("Email: " + user.getEmailAddress() + "\n\n");
        sb.append("This is an automaticly generated email.");

        return sb.toString();
    }

    private String buildResetPasswordNotification(String newPassword)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear User! your password has been changed successfully! \n\n");
        sb.append("Your new password is: " + newPassword + "\n\n");
        sb.append("This is an automaticly generated email.");

        return sb.toString();
    }
}