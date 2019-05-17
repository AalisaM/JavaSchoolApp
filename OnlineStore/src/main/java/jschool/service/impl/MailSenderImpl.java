package jschool.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jschool.dto.OrderDTO;
import jschool.service.MailSender;
import jschool.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


@Service("mailService")
public class MailSenderImpl implements MailSender {
    private static final Logger logger = Logger.getLogger(MailSenderImpl.class);

    private JavaMailSender mailSender;
    private UserService userService;
    private String storeAddress;
    private Configuration freemarkerConfig;

    @Autowired
    public MailSenderImpl(JavaMailSender mailSender, UserService userService, String storeAddress, Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.storeAddress = storeAddress;
        this.freemarkerConfig = freemarkerConfig;
    }

    /**
     * Sends email template from store mail account via smtp
     * @param login user login
     * @param password user password
     * @param fullname user fullName
     */
    @Override
    public void sendRegisterEmail(String login, String password, String fullname) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper  helper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

                Template t = freemarkerConfig.getTemplate("signup_template.ftl");
                Map<String,String> model = new HashMap();
                model.put("fullName", fullname);
                model.put("login",login);
                model.put("password",password);

                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

                helper.setTo(login);
                helper.setText(html, true);
                helper.setSubject("T-store registration");
                helper.setFrom(storeAddress);

                mailSender.send(message);
            } catch (Exception e) {
                logger.error(e.toString());
            }
        });
    }

    /**
     * Sends email template about order info to order owner
     * @param orderDTO order info to send
     */
    @Override
    public void sendOrderMail(OrderDTO orderDTO) {
        Executors.newSingleThreadExecutor().execute(() -> {
           try {
               MimeMessage message = mailSender.createMimeMessage();
               MimeMessageHelper  helper = new MimeMessageHelper(message,
                       MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                       StandardCharsets.UTF_8.name());
               freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

               Template t = freemarkerConfig.getTemplate("order_template.ftl");
               Map<String,Object> model = new HashMap();
               model.put("fullName", userService.getUserByEmail(orderDTO.getEmail()).getFullName());
               model.put("orderProduct", orderDTO.getOrderProducts());
               model.put("id", orderDTO.getId());

               String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

               helper.setTo(orderDTO.getEmail());
               helper.setText(html, true);
               helper.setSubject("T-store order");
               helper.setFrom(storeAddress);

               mailSender.send(message);
            } catch (Exception ex) {
               logger.error(ex.toString());
            }
        });

    }
}
