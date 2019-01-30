package phoenix.common.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author mate.karolyi
 */
@Profile("development")
@RestController
public class CommonControllerDevImpl extends CommonControllerImpl {

    private static final String UI_APP_URL = "http://localhost:4200";

    @Override
    public ResponseEntity<Void> confirmResetPassword(UUID token) {
        super.userService.resetPassword(token);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, UI_APP_URL).build();
    }

    @Override
    public ResponseEntity<Void> confirmSignup(UUID token) {
        super.userService.confirmSignup(token);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, UI_APP_URL).build();
    }

}
