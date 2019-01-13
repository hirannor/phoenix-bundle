package phoenix.core.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import phoenix.core.security.entity.UserPrincipal;
import phoenix.core.security.exception.AuthenticationMethodNotSupportedException;
import phoenix.core.security.exception.InvalidCredentialsException;
import phoenix.util.AuthenticationValidator;
import phoenix.util.ObjectMapperUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mate.karolyi
 */
public class PhoenixAjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler phoenixAuthenticationSuccessHandler;
    private AuthenticationFailureHandler phoenixAuthenticationFailureHandler;

    public PhoenixAjaxLoginProcessingFilter(String processingUrl, AuthenticationSuccessHandler phoenixAuthenticationSuccessHandler, AuthenticationFailureHandler phoenixAuthenticationFailureHandler) {
        super(processingUrl);
        this.phoenixAuthenticationSuccessHandler = phoenixAuthenticationSuccessHandler;
        this.phoenixAuthenticationFailureHandler = phoenixAuthenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!AuthenticationValidator.isValidPreAuthenticationRequest(request)) {
            throw new AuthenticationMethodNotSupportedException("Authentication Method not supported!");
        }

        UserPrincipal userPrincipal = (UserPrincipal) ObjectMapperUtil.deserialize(request, UserPrincipal.class);
        if (StringUtils.isBlank(userPrincipal.getUsername()) || StringUtils.isBlank(userPrincipal.getPassword())) {
            throw new InvalidCredentialsException("Username or password is not provided!");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal.getUsername(), userPrincipal.getPassword());

        Authentication authentication = this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        phoenixAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        phoenixAuthenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
