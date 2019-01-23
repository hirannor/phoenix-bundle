package phoenix.usermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.user.dto.User;
import phoenix.user.service.UserService;
import phoenix.usermanagement.controller.api.UsermanagementApi;

import javax.validation.Valid;
import java.util.List;

/**
 * Spring Controller Implementation of {@link UsermanagementApi}
 * @author mate.karolyi
 */
@RestController
@RequestMapping("/v1/api/")
@Secured("ROLE_ADMIN")
public class UserManagementControllerImpl implements UsermanagementApi {

    private UserService userService;

    public UserManagementControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userName) {
        userService.deleteUser(userName);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateUser(String userName, @Valid User user) {
        userService.updateUser(userName, user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

