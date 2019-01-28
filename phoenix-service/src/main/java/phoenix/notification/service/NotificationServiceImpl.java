package phoenix.notification.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Service implementation of {@link NotificationService}
 * @author mate.karolyi
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LogManager.getLogger(NotificationServiceImpl.class);
    private static final String MAIL_CONTENT_TYPE = "text/html";

    private JavaMailSender mailSender;
    private MimeMailMessage mimeMailMessage;

    public NotificationServiceImpl(JavaMailSender mailSender, MimeMailMessage mimeMailMessage) {
        this.mailSender = mailSender;
        this.mimeMailMessage = mimeMailMessage;
    }

    @Override
    public void sendMessage(String to, String msgBody) {
        try {
            //FIXME: Investigate why NPE occurs during notification sending
            MimeMessage mimeMessage = mimeMailMessage.getMimeMessage();
            mimeMessage.setContent(msgBody, MAIL_CONTENT_TYPE);
            mailSender.send(mimeMessage);
        } catch(MessagingException ex) {
            LOGGER.error(ex);
        }
    }
}
