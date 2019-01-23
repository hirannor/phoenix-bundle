package phoenix.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import phoenix.user.entity.User;
import phoenix.user.repository.UserRepository;

import java.util.Arrays;

/**
 * Custom implementation of {@link UserDetailsService}
 * Used for retrieving an user from a store during the authentication process.
 * @author mate.karolyi
 */
@Service("PhoenixUserDetailsService")
public class PhoenixUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public PhoenixUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("Username not found!");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleType().name());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Arrays.asList(authority));
    }
}
