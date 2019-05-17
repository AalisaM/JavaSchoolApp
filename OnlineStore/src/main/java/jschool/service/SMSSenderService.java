package jschool.service;

/**
 * SMS Service interface
 */
public interface SMSSenderService {

    /**
     * Sends sms by phone
     * @param to phonenumber
     * @param text text to send
     */
    void send(String to, String text);
}
