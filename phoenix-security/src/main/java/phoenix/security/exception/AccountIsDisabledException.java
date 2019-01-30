package phoenix.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mate.karolyi
 */
public class AccountIsDisabledException extends AuthenticationException {

    public AccountIsDisabledException(String message)
    {
        super(message);
    }

}
