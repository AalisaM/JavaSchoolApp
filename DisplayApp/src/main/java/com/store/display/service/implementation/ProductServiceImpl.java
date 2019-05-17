package com.store.display.service.implementation;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.store.display.controller.DisplayController;
import com.store.display.dto.ProductRestDTO;
import com.store.display.service.ProductService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Listener of queue
 */
@Stateless
public class ProductServiceImpl implements ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class);

    @Inject
    private PushServiceImpl pushServiceImpl;
    private List<ProductRestDTO> products = new LinkedList<>();

    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver;

    @Override
    public List<ProductRestDTO> getProduct(){
        return products;
    }

    @Override
    public void setProducts(List<ProductRestDTO> products) {
        this.products = products;
    }

    /**
     * Gets list of products from main app
     * @return List of products
     */
    @Override
    public List updateProducts(){
        try {
            //opens connection to main app
            java.net.URL url = new URL("http://" + getIp() + ":8086/store/restProduct");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new IOException();
            }
            //small data process
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String result = br.readLine().replace("null","\"\"");
            LinkedList<LinkedTreeMap> jsonObject = new Gson().fromJson(result, LinkedList.class);
            //converts json to product list
            this.products = new LinkedList<>();
            for (LinkedTreeMap product: jsonObject){
                ProductRestDTO p = new ProductRestDTO();
                p.setId((int) Math.floor((Double) product.get("id")));
                p.setImageSource((String) product.get("imageSource"));
                p.setDescription((String) product.get("desciption"));
                p.setPrice((Double) product.get("price"));
                p.setName((String) product.get("name"));
                products.add(p);
            }
            //closes connection
            conn.disconnect();
            log.info("update success");
        } catch (IOException e) {
            this.products = new LinkedList<>();
            log.info(e.toString());
        }
        pushServiceImpl.reload();
        return this.products;
    }

    /**
     * Sets listener
     */
    @PostConstruct
    public void receive() {
        updateProducts();
        log.info("in receive");
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://activemq:61616");
        props.put("queue.js-queue", "my_jms_queue");
        props.put("connectionFactoryNames", "queueCF");
        try {
            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
            Queue queue = (Queue) context.lookup("js-queue");
            connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            receiver = session.createReceiver(queue);
            receiver.setMessageListener(new DisplayController(this));
            log.info("in try receive");
        } catch (NamingException | JMSException e) {
            log.info(e.toString());
        }
    }

    /**
     * Closes listener
     */
    @PreDestroy
    public void destroyReceiver() {
        try {
            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.info(e.toString());
        }
    }

    /**
     * Specifies ip for image http service
     * @return ip
     */
    @Override
    public String getIp(){
        return "192.168.99.100";
    }
}
