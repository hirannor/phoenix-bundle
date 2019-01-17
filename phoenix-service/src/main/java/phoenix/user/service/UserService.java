package phoenix.user.service;

import phoenix.user.entity.User;

/**
 * User Service API
 * @author mate.karolyi
 */
public interface UserService {

    /**
     * Retrieves an {@link User} from a store
     * @param userName {@link String}
     * @return the currently authenticated {@link User}
     */
    User findByUserName(String userName);
}