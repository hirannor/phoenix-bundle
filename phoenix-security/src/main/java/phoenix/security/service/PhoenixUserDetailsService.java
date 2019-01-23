package phoenix.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import phoenix.user.entity.UserEntity;
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
        UserEntity userEntity = userRepository.findByUserName(username);
        if(userEntity == null)
        {
            throw new UsernameNotFoundException("Username not found!");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());
        return new User(userEntity.getUserName(), userEntity.getPassword(), Arrays.asList(authority));
    }
}
