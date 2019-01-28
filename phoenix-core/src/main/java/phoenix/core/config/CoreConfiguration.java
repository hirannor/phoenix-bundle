package phoenix.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage getSimpleMailMessage(EmailConfigurationProperties emailConfigurationProperties)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailConfigurationProperties.getUsername());
        simpleMailMessage.setSubject(EMAIL_SUBJECT);
        return simpleMailMessage;
    }
}
