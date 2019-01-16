package phoenix.user.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.user.controller.api.UserApi;
import phoenix.user.dto.User;
import phoenix.user.service.UserService;

/**
 * @author mate.karolyi
 */
@RestController
@RequestMapping("/v1/api/")
public class UserControllerImpl implements UserApi {

    private UserService userService;
    private ModelMapper modelMapper;

    public UserControllerImpl(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<User> getUser() {
        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<User>(modelMapper.map(userService.findByUserName(userName), User.class), null, HttpStatus.OK);
    }
}