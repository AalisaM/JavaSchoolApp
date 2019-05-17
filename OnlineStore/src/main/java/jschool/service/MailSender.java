package jschool.service;

import freemarker.template.TemplateException;
import jschool.dto.OrderDTO;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Emsil sender service interface
 */
public interface MailSender {

    /**
     * Send email about user registration
     * @param login user login
     * @param password user password
     * @param fullname user fullName
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    void sendRegisterEmail(String login, String password, String fullname) throws MessagingException, IOException, TemplateException;

    /**
     * Send email about order status changes
     * @param orderDTO order info to send
     */
    void sendOrderMail(OrderDTO orderDTO);
}
