package phoenix.user.service;


import phoenix.user.dto.User;
import phoenix.user.exception.UserAlreadyExistException;

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
    void addUser(User user) throws UserAlreadyExistException;
}