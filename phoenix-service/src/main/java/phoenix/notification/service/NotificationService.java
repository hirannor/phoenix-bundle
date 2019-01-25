package phoenix.notification.service;

/**
 * Notification Service API
 * @author mate.karolyi
 */
public interface NotificationService {

    /**
     * Sends message to the given destination address
     * @param to {@link String} destination address
     * @param msg {@link String} message body
     */
    void sendMessage(String to, String msg);
}
