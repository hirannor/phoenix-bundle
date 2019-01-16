package phoenix.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import phoenix.user.entity.User;

import java.security.Principal;

public interface UserController {

    @GetMapping("phoenix/user")
    User getPrincipal(Principal principal);

}