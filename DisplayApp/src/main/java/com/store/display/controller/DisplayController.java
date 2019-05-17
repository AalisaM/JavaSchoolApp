package com.store.display.controller;

import com.store.display.dto.ProductRestDTO;
import com.store.display.service.ProductService;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.*;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class DisplayController implements Serializable, MessageListener {
    private static final Logger log = Logger.getLogger(DisplayController.class);

    public DisplayController(ProductService productService){
        this.productService = productService;
    }

    public DisplayController(){

    }
    @EJB
    private ProductService productService;

    public List getProducts(){
        return productService.getProduct();
    }

    public void setProducts(List<ProductRestDTO> products) {
       this.productService.setProducts(products);
    }

    /**
     * catches messages from queue
    * */
    @Override
    public void onMessage(Message message) {
        log.info("caught message");
        productService.updateProducts();
    }

    public String getIp(){
        return productService.getIp();
    }
}

