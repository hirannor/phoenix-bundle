package phoenix.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception throw in case of Authentication method is not supported
 * @author mate.karolyi
 */
public class AuthenticationMethodNotSupportedException extends AuthenticationException {

    public AuthenticationMethodNotSupportedException(String message)
    {
        super(message);
    }

}
