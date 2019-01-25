package phoenix.role.service;

import java.util.List;

/**
 * Role Service API
 * @author mate.karolyi
 */
public interface RoleService {

    /**
     * Retrieves user roles
     * @return  roles
     */
    List<String> getRoles();
}
