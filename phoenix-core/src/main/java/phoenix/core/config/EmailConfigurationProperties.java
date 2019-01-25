package phoenix.core.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration properties class for Email
 * @author mate.karolyi
 */
@ConfigurationProperties(prefix = "phoenix.core.mail")
@PropertySource("classpath:mail.properties")
@Configuration
public class EmailConfigurationProperties {

    private String host = "127.0.0.1";
    private int port = 25;
    private String protocol = "smtp";
    private String username = "admin@localhost.com";
    private String password = "admin";
    private String auth = "mail.smtp.auth";

    /**
     * Retrieves the host name
     * @return host name
     */
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Retrieves the port number
     * @return port number
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Retrieves the mailing protocol
     * @return protocol
     */
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Retrieves the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the authentication mode
     * @return authentication
     */
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
