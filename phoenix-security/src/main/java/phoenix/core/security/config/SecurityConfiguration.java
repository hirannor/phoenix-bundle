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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import phoenix.core.security.filter.CsrfHeaderFilter;
import phoenix.core.security.filter.PhoenixAjaxLoginProcessingFilter;

/**
 *
 * @author mate.karolyi
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String MAIN_ENTRY_POINT = "/login";
    private static final String CSRF_TOKEN_HEADER = "X-XSRF-TOKEN";
    private static final String JSESSIONID = "JSESSIONID";
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/login",
            "/h2/**",
            "/index.html",
            "/favicon.ico",
            "/*.js"
//            "/main.js",
//            "/polyfills.js",
//            "/styles.js",
//            "/vendor.js",
//            "/runtime.js"
    };

    @Qualifier("PhoenixUserDetailsService")
    private UserDetailsService userDetailsService;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private LogoutSuccessHandler logoutSuccessHandler;
    private CsrfHeaderFilter csrfHeaderFilter;

    public SecurityConfiguration( @Qualifier("PhoenixUserDetailsService") UserDetailsService userDetailsService, AuthenticationEntryPoint authenticationEntryPoint,
                              LogoutSuccessHandler logoutSuccessHandler, CsrfHeaderFilter csrfHeaderFilter)
    {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.csrfHeaderFilter = csrfHeaderFilter;
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
                .csrf().csrfTokenRepository(csrfTokenRepository())
            .and()
                .httpBasic().disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and()
                .headers().frameOptions().sameOrigin()
            .and()
                .sessionManagement().enableSessionUrlRewriting(false).sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
                .logout().invalidateHttpSession(true).deleteCookies(JSESSIONID).logoutSuccessHandler(logoutSuccessHandler)
            .and()
                .addFilterBefore(phoenixAjaxLoginProcessingFilter(null, null), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(csrfHeaderFilter, CsrfFilter.class)
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().fullyAuthenticated();
    }

    @Bean
    public PhoenixAjaxLoginProcessingFilter phoenixAjaxLoginProcessingFilter(AuthenticationSuccessHandler phoenixAuthenticationSuccessHandler, AuthenticationFailureHandler phoenixAuthenticationFailureHandler) throws Exception {
        PhoenixAjaxLoginProcessingFilter phoenixAjaxLoginProcessingFilter = new PhoenixAjaxLoginProcessingFilter(MAIN_ENTRY_POINT, phoenixAuthenticationSuccessHandler, phoenixAuthenticationFailureHandler);
        phoenixAjaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return phoenixAjaxLoginProcessingFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository()
    {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(CSRF_TOKEN_HEADER);
        return repository;
    }
}
