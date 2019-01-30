package phoenix.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import phoenix.security.filter.PhoenixJwtAuthenticationFilter;
import phoenix.security.filter.PhoenixJwtAuthorizationFilter;

/**
 * Security Configuration for development. This configuration allows cors
 * @author mate.karolyi
 */
@EnableWebSecurity
@Configuration
@Profile("development")
public class SecurityCorsConfiguration extends SecurityConfiguration {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new PhoenixJwtAuthenticationFilter(MAIN_ENTRY_POINT, phoenixAuthenticationSuccessHandler, phoenixAuthenticationFailureHandler, authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new PhoenixJwtAuthorizationFilter(), BasicAuthenticationFilter.class)
            .authorizeRequests().antMatchers(PROTECTED_USER_API).hasAnyRole("ADMIN", "USER")
            .and()
                .authorizeRequests().antMatchers(PROTECTED_ADMIN_API).hasAnyRole("ADMIN")
                .antMatchers(PROTECTED_API).fullyAuthenticated();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addExposedHeader("Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, XSRF-TOKEN, Access-Control-Expose-Headers, set-cookie");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("http://localhost:4200");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
