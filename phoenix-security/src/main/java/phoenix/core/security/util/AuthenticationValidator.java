package phoenix.core.security.util;

import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mate.karolyi
 */
public class AuthenticationValidator {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static boolean isValidPreAuthenticationRequest(HttpServletRequest request)
    {
        if (!HttpMethod.POST.name().equals(request.getMethod()) || request.getHeader(CONTENT_TYPE) != null && !request.getHeader(CONTENT_TYPE).contains(CONTENT_TYPE_JSON))
        {
            return false;
        }
        return true;
    }

}
