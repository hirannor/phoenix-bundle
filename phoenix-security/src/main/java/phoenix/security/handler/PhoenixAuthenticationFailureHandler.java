package phoenix.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import phoenix.core.security.util.ObjectMapperUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This implementation handles the unsuccessful authentications
 * In case of unsuccessful authentication it sends back an json response to the client
 * and clears the {@link SecurityContextHolder}
 * @author mate.karolyi
 */
@Component
public class PhoenixAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ObjectMapperUtil.writeResponse("false", exception.getMessage(), HttpStatus.FORBIDDEN, response, null);
        SecurityContextHolder.clearContext();
    }
}
