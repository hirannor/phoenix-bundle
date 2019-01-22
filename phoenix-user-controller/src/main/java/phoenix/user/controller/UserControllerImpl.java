package phoenix.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.user.controller.api.UserApi;
import phoenix.user.dto.User;
import phoenix.user.service.UserService;

/**
 * Spring Controller Implementation of {@link UserApi}
 * @author mate.karolyi
 */
@RestController
@RequestMapping("/v1/api/")
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class UserControllerImpl implements UserApi {

    private UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<User> getUser() {
        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<User>(userService.findByUserName(userName), HttpStatus.OK);
    }
}