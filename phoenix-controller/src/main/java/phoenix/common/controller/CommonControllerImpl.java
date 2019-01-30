package phoenix.common.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import phoenix.common.controller.api.CommonApi;
import phoenix.user.dto.User;
import phoenix.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Spring Controller Implementation of {@link CommonApi}
 * @author mate.karolyi
 */
@RestController
public class CommonControllerImpl implements CommonApi {

    private static final Logger LOGGER = LogManager.getLogger(CommonControllerImpl.class);

    private UserService userService;
    private HttpServletRequest httpServletRequest;

    public CommonControllerImpl(UserService userService, HttpServletRequest httpServletRequest)
    {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ResponseEntity<Void> confirmResetPassword(UUID token) {
        userService.resetPassword(token);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, getAppUrl(httpServletRequest)).build();
    }

    @Override
    public ResponseEntity<Void> sendResetPasswordNotification(String userName) {
        userService.sendResetPasswordNotification(userName, getAppUrl(httpServletRequest) + "/common/resetPassword/");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> signup(@Valid User user) {
        userService.signup(user, getAppUrl(httpServletRequest) + "/common/signup/");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> confirmSignup(UUID token) {
        userService.confirmSignup(token);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, getAppUrl(httpServletRequest)).build();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://localhost:4200";
        // uncomment the below line for production build
        // return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
