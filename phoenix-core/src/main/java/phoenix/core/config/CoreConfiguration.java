package phoenix.core.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Core Configuration
 *
 * @author mate.karolyi
 */
@Configuration
@EnableConfigurationProperties(EmailConfigurationProperties.class)
public class CoreConfiguration implements WebMvcConfigurer {

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
    public MimeMailMessage getMimeMailMessage(EmailConfigurationProperties emailConfigurationProperties, JavaMailSender javaMailSender) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMailMessage mimeMailMessage = new MimeMailMessage(mimeMessage);
        mimeMailMessage.setFrom(emailConfigurationProperties.getUsername());
        mimeMailMessage.setSubject(EMAIL_SUBJECT);
        return mimeMailMessage;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/static/index.html");
                    }
                });
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }


    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
