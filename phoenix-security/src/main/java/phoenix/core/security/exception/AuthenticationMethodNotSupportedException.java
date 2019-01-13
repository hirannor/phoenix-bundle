package phoenix.core.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mate.karolyi
 */
public class AuthenticationMethodNotSupportedException extends AuthenticationException {

    public AuthenticationMethodNotSupportedException(String message)
    {
        super(message);
    }

}
