package phoenix.core.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mate.karolyi
 */
public class InvalidCredentialsException  extends AuthenticationException {

    public InvalidCredentialsException(String message)
    {
        super(message);
    }

}
