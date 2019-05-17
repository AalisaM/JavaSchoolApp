package jschool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jschool.dao.CartDAO;
import jschool.dao.CartItemDAO;
import jschool.dao.ProductDAO;
import jschool.dto.CartDTO;
import jschool.model.Cart;
import jschool.model.CartItem;
import jschool.model.Product;
import jschool.model.User;
import jschool.service.CartService;
import jschool.service.DTOConverterService;
import jschool.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = Logger.getLogger(CartServiceImpl.class);

    private DTOConverterService dtoConverterService;
    private UserService userService;
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private CartItemDAO cartItemDAO;

    @Autowired
    public CartServiceImpl(UserService userService, DTOConverterService dtoConverterService, CartDAO cartDAO, ProductDAO productDAO, CartItemDAO cartItemDAO) {
        this.userService = userService;
        this.dtoConverterService = dtoConverterService;
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        this.cartItemDAO = cartItemDAO;
    }


    /**
     * Gets Car entity by given id
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Cart findById(int id) {
        return  this.cartDAO.findById(id);
    }



    /**
     * Forms proper for rendering cart object from anonymous json cart
     * @param json cart of anon user {'totalAmount' : ,
     *             'totalPrice': , 'cartItemArr' : ['product_id': , amount : ,price]}
     * @return result cart map
     */
    @Override
    public LinkedHashMap<String,Object> formAnonymousCartByJSONString(String json){
        LinkedHashMap<String,Object> cart = new LinkedHashMap<>();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            cart.put("totalAmount",jsonNode.get("totalAmount").asInt());
            cart.put("totalPrice",jsonNode.get("totalPrice").asInt());
            LinkedList<LinkedHashMap> cartItemList = new LinkedList<>();
            JsonNode cartItemArr = jsonNode.get("cartItemArr");
            for (final JsonNode objNode : cartItemArr) {
                LinkedHashMap<String,Object> cartItemObj = new LinkedHashMap();
                cartItemObj.put("product_id",Integer.valueOf(objNode.get("product_id").asText()));
                cartItemObj.put("product_name",objNode.get("product_name").asText());
                cartItemObj.put("amount",Integer.valueOf(objNode.get("amount").asText()));
                cartItemObj.put("price",Integer.valueOf(objNode.get("price").asText()));
                cartItemList.add(cartItemObj);
            }
            cart.put("cartItem", cartItemList);
        }catch (Exception e){
            logger.error(e.toString());
        }
        logger.info(Arrays.toString(cart.keySet().toArray()));
        return cart;

    }

    /** Gets cart for user or sets new cart in case there was no cart at all
     * @return DTO object
     */
    @Override
    @Transactional
    public CartDTO getCurUserCart(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User curU = this.userService.getUserByEmail(auth.getName());
        if (!Objects.isNull(curU)){
            Cart cart =  findById(curU.getId());
            if (!Objects.isNull(cart)){
                return dtoConverterService.getCartDTO(cart);
            }else{
                Cart curC = setNewCartForUser(curU);
                return dtoConverterService.getCartDTO(curC);
            }
        }
        return null;
    }

    /**
     * Renew cart for User
     * @param curU current User
     * @return Cart Object
     */
    @Override
    @Transactional
    public Cart setNewCartForUser(User curU){
        Cart curC = new Cart();
        curC.setUser(curU);
        curC.setTotalPrice(0);
        curC.setTotalAmount(0);
        curC.setCartItem(new HashSet<>());
        logger.info("set new cart for user::" + curC.getUserId());
        this.cartDAO.add(curC);
        return curC;
    }

    /**
     * Merges current user cart with anonymous user cart json data
     * @param json anonymous user cart
     */
    @Override
    @Transactional
    public void mergeUserCartWithAnon(String json){
        LinkedHashMap<String,Object> anonCart = formAnonymousCartByJSONString(json);
        Cart curC = userService.getCurUser().getCart();
        if (Objects.isNull(curC)){
            curC = setNewCartForUser(userService.getCurUser());
        }
        Set<CartItem> userItems = (curC.getCartItem().isEmpty()) ? new LinkedHashSet<>() : curC.getCartItem();
        LinkedList<LinkedHashMap> cartItemList = (LinkedList<LinkedHashMap>) anonCart.get("cartItem");
        for ( LinkedHashMap obj : cartItemList) {
            Integer productId  = (Integer) obj.get("product_id");
            Integer amount = (Integer) obj.get("amount");
            Integer price = (Integer) obj.get("price");
            CartItem ci = userItems.stream().filter(cartItem -> productId.equals(cartItem.getProduct().getId())).findFirst().orElse(null);

            curC.setTotalAmount(curC.getTotalAmount() + amount);
            curC.setTotalPrice(curC.getTotalPrice() + price);

            if (!Objects.isNull(ci)){
                ci.setPrice(ci.getPrice() + price);
                ci.setAmount(ci.getAmount() + amount);
                cartItemDAO.updateCartItem(ci);
            }else {
                CartItem newCartItem = new CartItem();
                newCartItem.setAmount(amount);
                newCartItem.setPrice(price);
                Product newP= productDAO.findById(productId);
                if (!Objects.isNull(newP)){
                    newCartItem.setProduct(newP);
                }else{
                    continue;
                }
                newCartItem.setCart(curC);
                curC.getCartItem().add(newCartItem);
                cartItemDAO.addCartItem(newCartItem);
            }
        }
        cartDAO.update(curC);
    }

    /**Removes cart entity for logged user when order was made*/
    @Override
    @Transactional
    public void clearCartForCurUser(){
        try{
            cartDAO.remove( userService.getCurUser().getCart());
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

}
