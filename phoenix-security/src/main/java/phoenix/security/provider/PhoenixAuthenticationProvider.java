package phoenix.security.provider;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import phoenix.core.i18n.Resource;
import phoenix.security.exception.AccountIsDisabledException;
import phoenix.user.entity.User;
import phoenix.user.repository.UserRepository;

import java.util.Arrays;

/**
 * A custom implementation of {@link AuthenticationProvider}
 * @author mate.karolyi
 */
@Component
public class PhoenixAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Resource resource;

    public PhoenixAuthenticationProvider(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Resource resource)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.resource = resource;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String rawPassword = (String) authentication.getCredentials();

        User user = userRepository.findByUserName(username);
        if (user == null)
        {
            throw new AuthenticationCredentialsNotFoundException(resource.getMessage("phoenix.authentication.exception:CredentialsNotFound", null, LocaleContextHolder.getLocale()));
        }
        if (!isPasswordMatches(rawPassword, user.getPassword()))
        {
            throw new BadCredentialsException(resource.getMessage("phoenix.authentication.exception:BadCredentials", null, LocaleContextHolder.getLocale()));
        }
        if (user.getRole() == null)
        {
            throw new InsufficientAuthenticationException(resource.getMessage("phoenix.authentication.exception:InsufficientAuthentication", null, LocaleContextHolder.getLocale()));
        }
        if(!user.isActive())
        {
            throw new AccountIsDisabledException(resource.getMessage("phoenix.authentication.exception:AccountIsDisabled", null,  LocaleContextHolder.getLocale()));
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleType().getValue());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new UsernamePasswordAuthenticationToken(username, null, Arrays.asList(authority));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private boolean isPasswordMatches(String rawPassword, String encodedPassword)
    {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
