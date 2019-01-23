package phoenix.role.entity;

import phoenix.user.entity.User;

import javax.persistence.*;
import java.util.Set;

/**
 * role entity
 * @author mate.karolyi
 */
@Entity
@Table(name = "USR_ROLE")
public class Role {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
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
