package phoenix.core.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import phoenix.util.JwtConstants;
import phoenix.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static phoenix.util.JwtConstants.TOKEN_PREFIX;

public class PhoenixJwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConstants.HEADER_STRING);
        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = JwtTokenUtil.verifyToken(header.replace(TOKEN_PREFIX, ""));

        List<GrantedAuthority> grantedAuthorities = convertAuthorityStringToGrantedAuthority(decodedJWT.getClaims().get("AUTHORITIES").asList(String.class));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, grantedAuthorities));
        filterChain.doFilter(request, response);
    }

    private List<GrantedAuthority> convertAuthorityStringToGrantedAuthority(List<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(0);

        for(String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        return grantedAuthorities;
    }

}
