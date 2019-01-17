package phoenix.core.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown in case of invalid credentials during authentication
 * @author mate.karolyi
 */
public class InvalidCredentialsException  extends AuthenticationException {

    public InvalidCredentialsException(String message)
    {
        super(message);
    }

}
