package jschool.service;

import jschool.dto.CartDTO;
import jschool.model.Cart;
import jschool.model.User;

import java.util.LinkedHashMap;


/**
 * Cart Service interface
 */
public interface CartService {

     /**
      * Getting cart entity by id
      * @param id
      * @return
      */
     Cart findById(int id);

     /**
      * Forms proper for rendering cart object from anonymous json cart
      * @param json cart of anon user
      * @return result cart map
      */
     LinkedHashMap<String,Object> formAnonymousCartByJSONString(String json);

     /**
      * Renew cart for current User
      * @param curU current User
      * @return new cart
      */
     Cart setNewCartForUser(User curU);

     /**
      * Merges current user cart with anonymous user cart json data
      * @param json anonymous user cart
      */
     void mergeUserCartWithAnon(String json);

     /**
      * Gets DTO object for current user cart
      * @return
      */
     CartDTO getCurUserCart();

     /**
      * Removes cart for user
      */
     void clearCartForCurUser();
}
