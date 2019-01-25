package phoenix.notification.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link NotificationService}
 * @author mate.karolyi
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public NotificationServiceImpl(MailSender mailSender, SimpleMailMessage simpleMailMessage) {
        this.mailSender = mailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    @Override
    public void sendMessage(String to, String msgBody) {
        SimpleMailMessage message = new SimpleMailMessage(this.simpleMailMessage);
        message.setTo(to);
        message.setText(msgBody);

        mailSender.send(message);
    }
}
