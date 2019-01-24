package phoenix.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.role.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
