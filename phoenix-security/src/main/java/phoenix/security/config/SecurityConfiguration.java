package phoenix.security.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import phoenix.security.filter.PhoenixJwtAuthenticationFilter;
import phoenix.security.filter.PhoenixJwtAuthorizationFilter;
import phoenix.security.handler.PhoenixAuthenticationFailureHandler;
import phoenix.security.handler.PhoenixAuthenticationSuccessHandler;

/**
 * Security Configuration
 * @author mate.karolyi
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String PROTECTED_API = "/v1/api/**";
    private static final String PROTECTED_USER_API = "/v1/api/user/**";
    private static final String PROTECTED_ADMIN_API = "/v1/api/admin/**";

    private static final String MAIN_ENTRY_POINT = "/authenticate";
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/signup",
            "/h2/**",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
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
                .authorizeRequests().antMatchers(PROTECTED_USER_API).hasAnyRole("ADMIN", "USER")
                .and()
                    .authorizeRequests().antMatchers(PROTECTED_ADMIN_API).hasAnyRole("ADMIN", "USER")
                    .antMatchers(PROTECTED_API).fullyAuthenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
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
