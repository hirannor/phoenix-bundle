package phoenix.notification.service;

/**
 * Notification Service API
 * @author mate.karolyi
 */
public interface NotificationService {

    void sendMessage(String to, String msg);
}
