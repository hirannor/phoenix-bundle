package phoenix.notification.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    private JavaMailSender mailSender;
    private MimeMailMessage mimeMailMessage;

    public NotificationServiceImpl(JavaMailSender mailSender, MimeMailMessage mimeMailMessage) {
        this.mailSender = mailSender;
        this.mimeMailMessage = mimeMailMessage;
    }

    @Override
    public void sendMessage(String to, String msgBody) {
        try {
            MimeMessage mimeMessage = mimeMailMessage.getMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setText(msgBody, true);

            mailSender.send(mimeMessage);
        } catch(MessagingException ex) {
            LOGGER.error(ex);
        }
    }
}
