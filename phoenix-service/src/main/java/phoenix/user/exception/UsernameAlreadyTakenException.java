package phoenix.user.exception;

import phoenix.core.message.exception.MessageAwareException;

/**
 * Exception occurs when username is already taken
 * @author mate.karolyi
 */
public class UsernameAlreadyTakenException extends MessageAwareException {

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
