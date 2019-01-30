package phoenix.security.provider;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
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

    public PhoenixAuthenticationProvider(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String rawPassword = (String) authentication.getCredentials();

        User user = userRepository.findByUserName(username);
        if (user == null)
        {
            throw new AuthenticationCredentialsNotFoundException("No credentials found in context.");
        }
        if (!isPasswordMatches(rawPassword, user.getPassword()))
        {
            throw new BadCredentialsException("Authentication Failed. Wrong password is provided.");
        }
        if (user.getRole() == null)
        {
            throw new InsufficientAuthenticationException("User has no roles assigned.");
        }
        if(!user.isActive())
        {
            throw new AccountIsDisabledException("Account is parmanently disabled!");
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
