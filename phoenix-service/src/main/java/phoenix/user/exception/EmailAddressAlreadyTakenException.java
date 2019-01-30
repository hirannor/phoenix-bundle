package phoenix.user.exception;

import phoenix.core.message.exception.MessageAwareException;

/**
 * Exception occurs when email address is already taken
 * @author mate.karolyi
 */
public class EmailAddressAlreadyTakenException extends MessageAwareException {

    public EmailAddressAlreadyTakenException(String message) {
        super(message);
    }
}
