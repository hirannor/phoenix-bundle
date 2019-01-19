package phoenix.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static phoenix.security.util.JwtConstants.EXPIRATION;
import static phoenix.security.util.JwtConstants.SECRET_KEY;

/**
 * A Jwt utility class that is used for token generate and verify purpose
 * @author mate.karolyi
 */
public class JwtTokenUtil {

    private static final Logger LOGGER = LogManager.getLogger(JwtTokenUtil.class);

    public static String generateToken(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                    .withArrayClaim("role", convertGrantedAuthoritiesToString(user.getAuthorities()))
                    .sign(HMAC512(SECRET_KEY.getBytes()));
        }
        catch(JWTCreationException ex) {
            LOGGER.error("Invalid signing configuration / Couldn't convert Claims: {}", ex);
            throw ex;
        }
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
                    .build()
                    .verify(token);
        }
        catch(JWTVerificationException ex) {
            LOGGER.error("Couldn't verify token", ex);
            throw ex;
        }
    }

    private static String[] convertGrantedAuthoritiesToString(Collection<GrantedAuthority> grantedAuthorities) {
        List<String> authorities = new ArrayList<String>(0);
        for(GrantedAuthority grantedAuthority : grantedAuthorities) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[grantedAuthorities.size()]);
    }
}
