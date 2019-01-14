package phoenix.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import phoenix.user.entity.User;

import java.security.Principal;

public interface UserController {

    @GetMapping("user")
    User getPrincipal(Principal principal);
}
