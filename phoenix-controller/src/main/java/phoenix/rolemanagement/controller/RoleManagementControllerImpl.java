package phoenix.rolemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.role.service.RoleService;
import phoenix.rolemanagement.controller.api.RolemanagementApi;

import java.util.List;

/**
 * Spring Controller Implementation of {@link RolemanagementApi}
 * @author mate.karolyi
 */
@RestController
@RequestMapping("/v1/api/")
@Secured("ROLE_ADMIN")
public class RoleManagementControllerImpl implements RolemanagementApi {

    private RoleService roleService;

    public RoleManagementControllerImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<List<String>> getRoles() {
        return new ResponseEntity<List<String>>(roleService.getRoles(), HttpStatus.OK);
    }
}
