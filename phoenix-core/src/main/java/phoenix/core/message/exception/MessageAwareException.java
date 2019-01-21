package phoenix.core.message.exception;

/**
 * Base exception type for checked exceptions.
 * Checked exceptions should extend this class.
 */
public class MessageAwareException extends RuntimeException {

    public MessageAwareException(String message) {
        super(message);
    }

    public MessageAwareException(String message, Throwable cause) {
        super(message, cause);
    }
}
