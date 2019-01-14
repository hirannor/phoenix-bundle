package phoenix.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_DETAILS")
public class User {

    @Id
    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    private int age;

    @Column(name = "EMAILADDRESS")
    private String emailAddress;

    public User() {
        super();
    }

    public User(String userName, String firstName, String lastName, int age, String emailAddress) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.emailAddress = emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
