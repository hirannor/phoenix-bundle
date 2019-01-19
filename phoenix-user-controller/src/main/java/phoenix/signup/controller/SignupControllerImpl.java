package phoenix.signup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import phoenix.signup.controller.api.SignupApi;
import phoenix.user.dto.User;
import phoenix.user.service.UserService;

import javax.validation.Valid;

@RestController
public class SignupControllerImpl implements SignupApi {

    private UserService userService;

    public SignupControllerImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> signup(@Valid User user) {
        userService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
