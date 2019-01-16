package phoenix.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mate.karolyi
 */
@RestController
@RequestMapping("/v1/api/")
public class UserControllerImpl {
        //implements UserApi {

//    private UserService userService;
//    private ModelMapper modelMapper;
//
//    public UserControllerImpl(UserService userService, ModelMapper modelMapper) {
//        this.userService = userService;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public ResponseEntity<User> getUser() {
//        UserPrincipal userPrincipal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return modelMapper.mapModels(userService.findByUserName(userPrincipal.getUserName()), User.class);
//    }
}