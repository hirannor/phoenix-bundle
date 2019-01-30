package phoenix.user.entity;

import phoenix.role.entity.Role;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User entity
 * @author mate.karolyi
 */
@Entity
@Table(name = "USR_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USERNAME")
    private String userName;

    private String password;

    @ManyToOne
    @JoinTable(name="USR_ROLE", joinColumns = @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "ROLE",
                    referencedColumnName = "ROLE"))
    private Role role;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    private int age;

    private boolean active;

    @Column(name = "EMAILADDRESS", unique = true)
    private String emailAddress;

    public User()
    {
        super();
    }

    public User(String userName, String password, Role role, String firstName, String lastName, int age, String emailAddress, boolean active) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.emailAddress = emailAddress;
        this.active = active;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
