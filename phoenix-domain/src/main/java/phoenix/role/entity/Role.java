package phoenix.role.entity;

import phoenix.role.RoleType;
import phoenix.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * role entity
 * @author mate.karolyi
 */
@Entity
@Table(name = "ROL_ROLE")
public class Role implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_ID", nullable = false)
    private RoleType roleType;

    public Role() { super(); }

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @OneToMany(mappedBy="role")
    Set<User> users;
}
