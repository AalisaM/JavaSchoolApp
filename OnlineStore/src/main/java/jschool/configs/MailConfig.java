package jschool.configs;

import com.twilio.sdk.TwilioRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean(name="storeAddress")
    public String getStoreAddress(){
        return "tstore.manager@gmail.com";
    }

    @Bean(name = "mailSender")
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        //Using gmail
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(getStoreAddress());
        mailSender.setPassword("t-store1234");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean(name="twilioClient")
    public TwilioRestClient getTwilioClient(){
        return new TwilioRestClient("AC8baeb41f129583ee652fc5fb5cddf556",
                "0bc5b7af25c7b72d681448c3dddecbef");
    }

    @Bean(name="twilioNumber")
    public String getTwilioNumber(){
        return "+13212521337";
    }
}
