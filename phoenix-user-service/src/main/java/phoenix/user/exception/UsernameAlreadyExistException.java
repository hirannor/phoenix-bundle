package phoenix.user.exception;

/**
 * @author mate.karolyi
 */
public class UsernameAlreadyExistException extends Exception {

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
