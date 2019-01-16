package phoenix.core.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import phoenix.core.security.util.JwtTokenUtil;
import phoenix.core.security.util.ObjectMapperUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mate.karolyi
 */
@Component
public class PhoenixAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapperUtil.writeResponse("true", "You are successfuly logged in!", HttpStatus.OK, response, JwtTokenUtil.generateToken(authentication));
    }
}

