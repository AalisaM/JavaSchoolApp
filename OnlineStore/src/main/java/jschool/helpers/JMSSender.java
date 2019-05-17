package jschool.helpers;


import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import org.apache.log4j.Logger;

/**
 * Service, reposible for sending message to activemq queue
 * */
public class JMSSender {
    private static final Logger logger = Logger.getLogger(JMSSender.class);

    /**
     * Sends message to activemq
     * */
    public static void send() {

        Hashtable<String, String> props = new Hashtable<>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://activemq:61616");
        props.put("queue.js-queue", "my_jms_queue");
        props.put("connectionFactoryNames", "queueCF");
        try {

            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
            Queue queue = (Queue) context.lookup("js-queue");

            QueueConnection connection = connectionFactory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

            QueueSender sender = session.createSender(queue);
            TextMessage message = session.createTextMessage("update top");

            sender.send(message);
            sender.close();
            session.close();
            connection.close();
            logger.info("OK sent to acctivemq");
        } catch (Exception e) {
            logger.error("Exception, achtung. Could not sent to acctivemq");
        }
    }

}
