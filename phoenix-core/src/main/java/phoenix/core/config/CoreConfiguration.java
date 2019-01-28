package phoenix.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Core Configuration
 * @author mate.karolyi
 */
@Configuration
@EnableConfigurationProperties(EmailConfigurationProperties.class)
public class CoreConfiguration {

    private final static String EMAIL_SUBJECT = "NOREPLY";

    @Bean
    public JavaMailSender getJavaMailSender(EmailConfigurationProperties emailConfigurationProperties) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(emailConfigurationProperties.getHost());
        javaMailSender.setPort(emailConfigurationProperties.getPort());
        javaMailSender.setProtocol(emailConfigurationProperties.getProtocol());
        javaMailSender.setUsername(emailConfigurationProperties.getUsername());
        javaMailSender.setPassword(emailConfigurationProperties.getPassword());

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return javaMailSender;
    }

    @Bean
    public MimeMailMessage getMimeMailMessage(EmailConfigurationProperties emailConfigurationProperties, JavaMailSender javaMailSender)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMailMessage mimeMailMessage = new MimeMailMessage(mimeMessage);
        mimeMailMessage.setFrom(emailConfigurationProperties.getUsername());
        mimeMailMessage.setSubject(EMAIL_SUBJECT);
        return mimeMailMessage;
    }
}
