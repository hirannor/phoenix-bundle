package phoenix.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import phoenix.security.util.JwtTokenUtil;
import phoenix.security.util.ObjectMapperUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This implementation handles the successful authentication.
 * In case of successful authentication it sends back an json response to the client
 * with the appropiate Jwt token
 * @author mate.karolyi
 */
@Component
public class PhoenixAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapperUtil.writeResponse("true", "You are successfuly logged in!", HttpStatus.OK, response, JwtTokenUtil.generateToken(authentication));
    }
}

