package phoenix.core.security.entrypoint;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import phoenix.core.security.util.ObjectMapperUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Unauthorized access point
 *
 * @author mate.karolyi
 */
@Component
public class PhoenixUnauthorizedAccessEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapperUtil.writeResponse("false", "You dont have rights to view this page!", HttpStatus.FORBIDDEN, response, null);

    }
}
