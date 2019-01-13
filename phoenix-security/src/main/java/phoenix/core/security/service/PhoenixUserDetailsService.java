package phoenix.core.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import phoenix.core.security.entity.UserPrincipal;
import phoenix.core.security.repository.AuthenticationCredentialsRepository;

import java.util.Arrays;

/**
 * @author mate.karolyi
 */
@Service("PhoenixUserDetailsService")
public class PhoenixUserDetailsService implements UserDetailsService {

    private AuthenticationCredentialsRepository authenticationCredentialsRepository;

    public PhoenixUserDetailsService(AuthenticationCredentialsRepository authenticationCredentialsRepository)
    {
        this.authenticationCredentialsRepository = authenticationCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = authenticationCredentialsRepository.findByUsername(username);
        if(userPrincipal == null)
        {
            throw new UsernameNotFoundException("Username not found!");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(userPrincipal.getRole());
        return new User(userPrincipal.getUsername(), userPrincipal.getPassword(), Arrays.asList(authority));
    }
}
