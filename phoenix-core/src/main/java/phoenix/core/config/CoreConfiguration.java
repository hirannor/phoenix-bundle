package phoenix.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(EmailConfigurationProperties.class)
public class CoreConfiguration {

    private final static String EMAIL_SUBJECT = "noreply";

    @Bean
    public JavaMailSender getJavaMailSender(EmailConfigurationProperties emailConfigurationProperties) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(emailConfigurationProperties.getHost());
        javaMailSender.setPort(emailConfigurationProperties.getPort());
        javaMailSender.setProtocol(emailConfigurationProperties.getProtocol());
        javaMailSender.setUsername(emailConfigurationProperties.getUsername());
        javaMailSender.setPassword(emailConfigurationProperties.getPassword());

        Properties prop = new Properties();
        prop.setProperty(emailConfigurationProperties.getAuth(), "true");

        javaMailSender.setJavaMailProperties(prop);

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
