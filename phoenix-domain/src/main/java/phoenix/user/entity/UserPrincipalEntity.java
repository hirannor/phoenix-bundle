package phoenix.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserPrincipalEntity entity, used for authentication process
 * @author mate.karolyi
 */
@Entity
@Table(name = "USER_AUTH_DETAILS")
public class UserPrincipalEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USERNAME")
    private String userName;

    private String password;

    private String role;

    public UserPrincipalEntity()
    {
        super();
    }

    public UserPrincipalEntity(String userName, String password, String role) {
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
