package phoenix.core.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserPrincipal entity, used for authentication process
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
    @Column(name = "USERNAME")
    private String userName;
    private String password;
    private String role;

    public UserPrincipal(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
