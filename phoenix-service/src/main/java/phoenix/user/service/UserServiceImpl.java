package phoenix.user.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import phoenix.notification.service.NotificationService;
import phoenix.role.RoleType;
import phoenix.role.entity.Role;
import phoenix.user.dto.User;
import phoenix.user.entity.ResetPasswordToken;
import phoenix.user.exception.UserAlreadyExistException;
import phoenix.user.exception.UserNotFoundException;
import phoenix.user.repository.ResetPasswordTokenRepository;
import phoenix.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation of {@link UserService}
 * @author mate.karolyi
 */
@Service
public class UserServiceImpl implements UserService {

    private static final int TOKEN_EXPIRY_IN_MINUTES = 30;

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private NotificationService notificationService;
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, NotificationService notificationService, ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
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
    public void addUser(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new UserAlreadyExistException("User already exist!");
        }

        phoenix.user.entity.User userEntity = new phoenix.user.entity.User();

        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleType(RoleType.ROLE_USER);
        userEntity.setRole(role);

        userRepository.save(userEntity);
        notificationService.sendMessage(user.getEmailAddress(), buildRegistrationNotification(userEntity));
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
        if (userRepository.findByUserName(userName) == null) {
            throw new UserNotFoundException("User with the given username is not found");
        }
        phoenix.user.entity.User userEntity = new phoenix.user.entity.User();
        BeanUtils.copyProperties(user, userEntity);

        Role role = new Role();
        role.setRoleType(RoleType.valueOf(user.getRole()));
        userEntity.setRole(role);

        userRepository.save(userEntity);
    }

    @Override
    public void resetPassword(UUID token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token.toString());

        if (resetPasswordToken == null) {
            throw new IllegalArgumentException("Reset password token not found");
        }

        Date currentDate = new Date();

        if(currentDate.after(resetPasswordToken.getExpiryDate())) {
            throw new IllegalArgumentException("Reset password token is expired");
        }

        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);

        phoenix.user.entity.User userEntity = resetPasswordToken.getUser();
        userEntity.setPassword(bCryptPasswordEncoder.encode(generatedPassword));
        userRepository.save(userEntity);

        notificationService.sendMessage(userEntity.getEmailAddress(), buildPasswordChangeNotification(userEntity, generatedPassword));
    }

    @Override
    public void sendResetPasswordNotification(String userName, String resetPasswordUrl) {
        phoenix.user.entity.User userEntity = userRepository.findByUserName(userName);

        if (userEntity == null) {
            throw new UserNotFoundException("User with the given username is not found");
        }

        String token = UUID.randomUUID().toString();

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setExpiryDate(DateUtils.addMinutes(new Date(), TOKEN_EXPIRY_IN_MINUTES));
        resetPasswordToken.setToken(token);
        resetPasswordToken.setUser(userEntity);
        resetPasswordTokenRepository.save(resetPasswordToken);

        notificationService.sendMessage(userEntity.getEmailAddress(), buildResetPasswordNotification(resetPasswordUrl + token));
    }

    private String buildPasswordChangeNotification(phoenix.user.entity.User userEntity, String rawPassword) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Dear " + userEntity.getFirstName() + " " + userEntity.getLastName() + " your password was changed successfuly!</h1><br>");
        sb.append("<p>Your new password is: " + rawPassword + "</p>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }

    private String buildRegistrationNotification(phoenix.user.entity.User userEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>Dear " + userEntity.getFirstName() + " " + userEntity.getLastName() + " your registration was successful!</h1><br>");
        sb.append("<p>Your details:</p><br>");
        sb.append("<ul>");
        sb.append("<li>Username: " + userEntity.getUserName() + "</li>");
        sb.append("<li>First Name: " + userEntity.getFirstName() + "</li>");
        sb.append("<li>Last Name: " + userEntity.getFirstName() + "</li>");
        sb.append("<li>Age: " + userEntity.getAge() + "</li>");
        sb.append("<li>E-mail: " + userEntity.getEmailAddress() + "</li>");
        sb.append("</ul>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }

    private String buildResetPasswordNotification(String callbackUrl) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>To reset your password please click on the below</h1></br><a href=\"" + callbackUrl + ">link</a>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }
}