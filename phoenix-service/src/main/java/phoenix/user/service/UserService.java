package phoenix.user.service;


import phoenix.user.dto.User;

import java.util.List;
import java.util.UUID;

/**
 * User Service API
 * @author mate.karolyi
 */
public interface UserService {

    /**
     * Retrieves a {@link User} from a store
     * @param userName {@link String}
     * @return the currently authenticated {@link User}
     */
    User findByUserName(String userName);

    /**
     * Add a user to the store
     * @param user {@link User}
     */
    void addUser(User user);

    /**
     * Returns all users from a store
     * @return  list of users
     */
    List<User> getUsers();

    /**
     * Deletes a user by username
     * @param userName {@link String}
     */
    void deleteUser(String userName);

    /**
     * Update an existing user
     * @param userName
     * @param user {@link User} to be updated
     */
    void updateUser(String userName, User user);

    /**
     * Resets password for a given user, which is associated with the token
     * @param token {@link UUID}
     */
    void resetPassword(UUID token);

    /**
     * Sends a reset password notification trough email for the given user
     * @param userName {@link String}
     * @param resetPasswordUrl {@link String}
     */
    void sendResetPasswordNotification(String userName, String resetPasswordUrl);
}