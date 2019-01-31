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

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.validation.Valid;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.UUID;

/**
 * Spring Controller Implementation of {@link CommonApi}
 * @author mate.karolyi
 */
@RestController
public class CommonControllerImpl implements CommonApi {

    private static final Logger LOGGER = LogManager.getLogger(CommonControllerImpl.class);

    private UserService userService;

    public CommonControllerImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> confirmResetPassword(UUID token) {
        userService.resetPassword(token);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, getAppUrl()).build();
    }

    @Override
    public ResponseEntity<Void> sendResetPasswordNotification(String userName) {
        userService.sendResetPasswordNotification(userName, getAppUrl() + "/common/resetPassword/");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> signup(@Valid User user) {
        userService.signup(user, getAppUrl() + "/common/signup/");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> confirmSignup(UUID token) {
        userService.confirmSignup(token);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, getAppUrl()).build();
    }

    private String getAppUrl() {
        String appUrl = null;
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                    Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            String host = InetAddress.getLocalHost().getHostAddress();
            String port = objectNames.iterator().next().getKeyProperty("port");

            appUrl = "http" + "://" + host + ":" + port;

        }
        catch(MalformedObjectNameException | UnknownHostException ex) {
            LOGGER.error(ex);
        }

        return appUrl;
    }
}
