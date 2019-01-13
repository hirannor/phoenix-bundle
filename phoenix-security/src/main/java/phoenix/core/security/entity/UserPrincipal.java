package phoenix.core.security.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mate.karolyi
 */
@Entity
@Table(name = "USER_AUTH_DETAILS")
public class UserPrincipal {

    public UserPrincipal()
    {
        super();
    }

    @Id
    private String username;
    private String password;
    private String role;

    public UserPrincipal(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
