package phoenix.role.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import phoenix.role.entity.Role;
import phoenix.role.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation of {@link RoleService}
 * @author mate.karolyi
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<String> getRoles() {
        List<String> roles = new ArrayList<String>(0);
        List<Role> roleEntities =  roleRepository.findAll();

        for (Role roleEntity : roleEntities) {
            roles.add(roleEntity.getRoleType().getValue());
        }
        return roles;
    }
}
