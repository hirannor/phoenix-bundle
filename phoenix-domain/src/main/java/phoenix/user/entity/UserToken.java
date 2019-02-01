package phoenix.user.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "USR_TOKEN_CONFIRM")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USERNAME")
    private User user;

    private Date expiryDate;

    @Enumerated(EnumType.STRING)
    private UserConfirmOperation userConfirmOperation;

    public UserToken() {
        super();
    }

    public UserToken(UUID token, User user, Date expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserConfirmOperation getUserConfirmOperation() {
        return userConfirmOperation;
    }

    public void setUserConfirmOperation(UserConfirmOperation userConfirmOperation) {
        this.userConfirmOperation = userConfirmOperation;
    }
}
