package phoenix.user.exception;

import phoenix.core.message.exception.MessageAwareException;

/**
 * @author mate.karolyi
 */
public class UserAlreadyExistException extends MessageAwareException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
