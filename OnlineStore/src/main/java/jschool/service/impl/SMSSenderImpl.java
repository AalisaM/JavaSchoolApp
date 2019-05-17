package jschool.service.impl;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import jschool.service.SMSSenderService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Service
public class SMSSenderImpl implements SMSSenderService {
    private static final Logger logger = Logger.getLogger(SMSSenderImpl.class);

    private TwilioRestClient twilioClient;
    private String twilioNumber;

    @Autowired
    public SMSSenderImpl(TwilioRestClient twilioClient, String twilioNumber) {
        this.twilioClient = twilioClient;
        this.twilioNumber = twilioNumber;
    }

    /**
     * Sends sms to phone
     * @param phoneTo phonenumber
     * @param text text to send
     */
    @Override
    public void send(String phoneTo, String text) {

        String to = "+79955984506";
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Build a filter for the MessageList
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("Body", text));
                params.add(new BasicNameValuePair("To", to));
                params.add(new BasicNameValuePair("From", twilioNumber));

                MessageFactory messageFactory = twilioClient.getAccount().getMessageFactory();
                Message message = messageFactory.create(params);
                logger.info(message.getSid());
            }
            catch (Exception e) {
                logger.info(e.toString());
            }
        });
    }
}
