package phoenix.user.exception;

import phoenix.core.message.exception.MessageAwareException;

/**
 * Exception occurs when user already exist in a store
 * @author mate.karolyi
 */
public class UserAlreadyExistException extends MessageAwareException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
