package phoenix.core.message.exception;

/**
 * Base exception type
 * @author mate.karolyi
 */
public class MessageAwareException extends RuntimeException {

    public MessageAwareException(String message) {
        super(message);
    }

    public MessageAwareException(String message, Throwable cause) {
        super(message, cause);
    }
}
