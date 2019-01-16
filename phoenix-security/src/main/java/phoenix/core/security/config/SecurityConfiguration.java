package phoenix.core.security.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import phoenix.core.security.filter.PhoenixJwtAuthenticationFilter;
import phoenix.core.security.filter.PhoenixJwtAuthorizationFilter;
import phoenix.core.security.handler.PhoenixAuthenticationFailureHandler;
import phoenix.core.security.handler.PhoenixAuthenticationSuccessHandler;
import phoenix.core.security.repository.AuthenticationCredentialsRepository;

/**
 *
 * @author mate.karolyi
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String PROTECTED_API = "/v1/api/**";
    private static final String MAIN_ENTRY_POINT = "/authenticate";
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/authenticate",
            "/h2/**",
            "/index.html",
            "/favicon.ico",
            "/*.js"
    };

    @Qualifier("PhoenixUserDetailsService")
    private UserDetailsService userDetailsService;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private PhoenixAuthenticationSuccessHandler phoenixAuthenticationSuccessHandler;
    private PhoenixAuthenticationFailureHandler phoenixAuthenticationFailureHandler;

    public SecurityConfiguration(@Qualifier("PhoenixUserDetailsService") UserDetailsService userDetailsService, AuthenticationEntryPoint authenticationEntryPoint,
                                 PhoenixAuthenticationSuccessHandler phoenixAuthenticationSuccessHandler, PhoenixAuthenticationFailureHandler phoenixAuthenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.phoenixAuthenticationSuccessHandler = phoenixAuthenticationSuccessHandler;
        this.phoenixAuthenticationFailureHandler = phoenixAuthenticationFailureHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean("authenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

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
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(PROTECTED_API).fullyAuthenticated();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
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
