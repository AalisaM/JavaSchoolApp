package jschool.service;

import jschool.validator.Message;

/**
 * Interface for cart item sevice
 */
public interface CartItemService {
     /**
      * This method adds product to cur cart entity
      * @param m Message information
      * @param json input json with product data
      * @return result info message object
      */
     Message addProductToCart(Message m, String json);

     /**
      * This method removes product from cart
      * @param m Message information
      * @param json input json with product data
      * @returnr result info message object
      */
     Message removeProductFromCart(Message m, String json);

}
