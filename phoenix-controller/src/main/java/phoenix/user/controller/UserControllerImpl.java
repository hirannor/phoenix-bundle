package phoenix.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.user.entity.User;
import phoenix.user.service.UserService;

import java.security.Principal;

/**
 * @author mate.karolyi
 */
@RestController
@RequestMapping("/v1/api/")
public class UserControllerImpl implements UserController {

    private UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public User getPrincipal(Principal principal)
    {
        return userService.findByUserName(principal.getName());
    }
}