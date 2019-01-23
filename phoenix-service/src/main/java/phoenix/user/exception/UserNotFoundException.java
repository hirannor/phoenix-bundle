package phoenix.user.exception;

import phoenix.core.message.exception.MessageAwareException;

public class UserNotFoundException extends MessageAwareException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
