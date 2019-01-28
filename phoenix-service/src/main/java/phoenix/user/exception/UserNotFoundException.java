package phoenix.user.exception;

import phoenix.core.message.exception.MessageAwareException;

/**
 * Exception occurs when the user is not found in a store
 * @author mate.karolyi
 */
public class UserNotFoundException extends MessageAwareException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
