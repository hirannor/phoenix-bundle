package phoenix.security.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * UserPrincipal entity, used for authentication process
 * @author mate.karolyi
 */
@Entity
@Table(name = "USER_AUTH_DETAILS")
@IdClass(UserPrincipal.AssignedEmailAddress.class)
public class UserPrincipal {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USERNAME")
    private String userName;

    @Id
    @Column(name = "EMAILADDRESS", unique = true)
    private String emailAddress;

    private String password;
    private String role;

    public UserPrincipal()
    {
        super();
    }

    public UserPrincipal(String userName, String emailAddress, String password, String role) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public static class AssignedEmailAddress implements Serializable {
        private String userName;
        private String emailAddress;

        public AssignedEmailAddress() {}

        public AssignedEmailAddress(String userName, String emailAddress) {
            this.userName = userName;
            this.emailAddress = emailAddress;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AssignedEmailAddress that = (AssignedEmailAddress) o;
            return Objects.equals(userName, that.userName) &&
                    Objects.equals(emailAddress, that.emailAddress);
        }

        @Override
        public int hashCode() {

            return Objects.hash(userName, emailAddress);
        }
    }
}
