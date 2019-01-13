package phoenix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author mate.karolyi
 */
@RestController
public class UserController {

    @GetMapping("user")
    public Principal getPrincipal(Principal principal)
    {
        return principal;
    }
}
