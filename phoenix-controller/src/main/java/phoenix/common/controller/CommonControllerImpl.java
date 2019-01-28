package phoenix.common.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import phoenix.common.controller.api.CommonApi;
import phoenix.user.dto.User;
import phoenix.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity<Void> resetPassword(UUID token) {
        URI location = null;
        userService.resetPassword(token);
        try {
            location = new URI(getAppUrl(httpServletRequest));
        } catch (URISyntaxException ex) {
            LOGGER.error(ex);
        }

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> sendConfirmation(String userName) {
        userService.sendResetPasswordNotification(userName, getAppUrl(httpServletRequest) + "/common/resetPassword/");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> signup(@Valid User user) {
        userService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
