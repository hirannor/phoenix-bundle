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
import phoenix.user.entity.UserConfirmOperation;
import phoenix.user.entity.UserToken;
import phoenix.user.exception.EmailAddressAlreadyTakenException;
import phoenix.user.exception.UserNotFoundException;
import phoenix.user.exception.UsernameAlreadyTakenException;
import phoenix.user.repository.UserRepository;
import phoenix.user.repository.UserTokenRepository;

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
    private UserTokenRepository userTokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, NotificationService notificationService, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userTokenRepository = userTokenRepository;
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
    public void signup(User user, String confirmSignupUrl) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new UsernameAlreadyTakenException("Username is already taken. Please choose another one!");
        }
        if(userRepository.findByEmailAddress(user.getEmailAddress()) != null) {
            throw new EmailAddressAlreadyTakenException("Email address is already taken. Please use another one!");
        }

        phoenix.user.entity.User userEntity = new phoenix.user.entity.User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleType(RoleType.ROLE_USER);
        userEntity.setRole(role);

        userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();
        createAndSaveUserToken(token, UserConfirmOperation.SIGNUP, userEntity);
        
        notificationService.sendMessage(user.getEmailAddress(), buildRegistrationNotification(userEntity, confirmSignupUrl + token));
    }

    @Override
    public void confirmSignup(UUID token) {
        UserToken userToken = validateToken(token);

        phoenix.user.entity.User user =  userToken.getUser();
        user.setActive(true);
        userRepository.save(user);

        notificationService.sendMessage(user.getEmailAddress(), buildSignupConfirmationNotification(user));
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
        UserToken userToken = validateToken(token);

        String generatedPassword = RandomStringUtils.randomAlphanumeric(8);

        phoenix.user.entity.User userEntity = userToken.getUser();
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
        createAndSaveUserToken(token, UserConfirmOperation.RESET_PASSWORD, userEntity);

        notificationService.sendMessage(userEntity.getEmailAddress(), buildResetPasswordNotification(resetPasswordUrl + token));
    }

    private void createAndSaveUserToken(String token, UserConfirmOperation userConfirmOperation, phoenix.user.entity.User userEntity) {
        UserToken userToken = new UserToken();
        userToken.setExpiryDate(DateUtils.addMinutes(new Date(), TOKEN_EXPIRY_IN_MINUTES));
        userToken.setToken(token);
        userToken.setUser(userEntity);
        userToken.setUserConfirmOperation(userConfirmOperation);
        userTokenRepository.save(userToken);
    }

    private UserToken validateToken(UUID token) {
        UserToken userToken = userTokenRepository.findByToken(token.toString());
        if (userToken == null) {
            throw new IllegalArgumentException("Token not found");
        }
        Date currentDate = new Date();
        if(currentDate.after(userToken.getExpiryDate())) {
            throw new IllegalArgumentException("Token is expired");
        }

        return userToken;
    }

    private String buildPasswordChangeNotification(phoenix.user.entity.User userEntity, String rawPassword) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Dear " + userEntity.getFirstName() + " " + userEntity.getLastName() + " your password was changed successfuly!</h1>");
        sb.append("<br>");
        sb.append("<p>Your new password is: " + rawPassword + "</p>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }

    private String buildRegistrationNotification(phoenix.user.entity.User userEntity, String callbackUrl) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>Dear " + userEntity.getFirstName() + " " + userEntity.getLastName() + " your registration was successful!</h1>");
        sb.append("<br>");
        sb.append("<p>Your details:</p>");
        sb.append("<br>");
        sb.append("<ul>");
        sb.append("<li>Username: " + userEntity.getUserName() + "</li>");
        sb.append("<li>First Name: " + userEntity.getFirstName() + "</li>");
        sb.append("<li>Last Name: " + userEntity.getFirstName() + "</li>");
        sb.append("<li>Age: " + userEntity.getAge() + "</li>");
        sb.append("<li>E-mail: " + userEntity.getEmailAddress() + "</li>");
        sb.append("</ul>");
        sb.append("<br>");
        sb.append("<h1>In order to activate your account please click on the below</h1>");
        sb.append("<a href=" + callbackUrl + ">link</a>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }

    private String buildSignupConfirmationNotification(phoenix.user.entity.User userEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>Dear " + userEntity.getFirstName() + " " + userEntity.getLastName() + " your account was succesfuly activated!</h1>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }

    private String buildResetPasswordNotification(String callbackUrl) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h1>To reset your password please click on the below</h1>");
        sb.append("<a href=" + callbackUrl + ">link</a>");
        sb.append("<br>");
        sb.append("<p>This is an automaticly generated email.</p>");

        return sb.toString();
    }
}