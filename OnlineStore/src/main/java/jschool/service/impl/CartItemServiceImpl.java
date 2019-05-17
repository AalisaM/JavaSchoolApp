package jschool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import jschool.dao.CartDAO;
import jschool.dao.CartItemDAO;
import jschool.dao.ProductDAO;
import jschool.model.Cart;
import jschool.model.CartItem;
import jschool.model.Product;
import jschool.service.CartItemService;
import jschool.service.CartService;
import jschool.validator.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;


@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private static final Logger logger = Logger.getLogger(CartItemServiceImpl.class);

    private final CartService cartService;
    private final CartDAO cartDAO;
    private final CartItemDAO cartItemDAO;
    private final ProductDAO productDAO;

    @Autowired
    public CartItemServiceImpl(CartService cartService, CartDAO cartDAO, CartItemDAO cartItemDAO, ProductDAO productDAO) {
        this.cartService = cartService;
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.productDAO = productDAO;
    }

    /**
     * This message adds product to cart
     * @param m Message information
     * @param json input json with product data {"id" : }
     * @return result info message object
     */
    @Override
    public Message addProductToCart(Message m, String json){
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("wrong json for adding");
            return m;
        }
        Cart curC = this.cartDAO.findById(this.cartService.getCurUserCart().getUser_id());
        Product p =  this.productDAO.findById(Integer.parseInt(jsonNode.get("id").asText()));
        CartItem ci = this.cartDAO.findCartItemByProductId(Integer.parseInt(jsonNode.get("id").asText()), curC.getUserId());
        if (Objects.isNull(ci)){
            ci = new CartItem();
            ci.setAmount(1);
            ci.setCart(curC);
            ci.setPrice(p.getPrice());
            ci.setProduct(p);
            cartItemDAO.addCartItem(ci);
        }else{
            ci.setPrice(ci.getPrice() +  p.getPrice());
            ci.setAmount(ci.getAmount() +1);
            cartItemDAO.updateCartItem(ci);
        }
        curC.setTotalAmount(curC.getTotalAmount() + 1);
        curC.setTotalPrice(curC.getTotalPrice() + p.getPrice());
        cartDAO.update(curC);
        m.getConfirms().add("product was successfully added");
        return m;
    }

    /**
     * Method removes product from cart
     * @param m Message information
     * @param json input json with product data {'id' : , 'amount' : }
     * @return message info object
     */
    @Override
    public Message removeProductFromCart(Message m, String json){
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        if (Objects.isNull(jsonNode)){
            m.getErrors().add("wrong data");
            return m;
        }

        int productid = Integer.parseInt(jsonNode.get("id").asText());
        int amount = Integer.parseInt(jsonNode.get("amount").asText());
        int cartID = this.cartService.getCurUserCart().getUser_id();

        Cart curC = this.cartDAO.findById(cartID);
        Product p =  this.productDAO.findById(productid);
        CartItem ci = this.cartDAO.findCartItemByProductId(productid, curC.getUserId());

        if (Objects.isNull(ci)){
            m.getWarnings().add("no cartitems in cart for this product found");
            return m;
        }else{
            logger.info(ci.getAmount());
            logger.info(amount);
            if (ci.getAmount() > amount) {
                ci.setPrice(ci.getPrice() - p.getPrice()*amount);
                ci.setAmount(ci.getAmount() - amount);
                curC.getCartItem().add(ci);
                cartItemDAO.updateCartItem(ci);
            }else{
                curC.getCartItem().remove(ci);
                p.getCartItem().remove(ci);
                cartItemDAO.removeCartItemByID(ci);
            }
        }
        if (curC.getTotalAmount() >= amount) {
            curC.setTotalAmount(curC.getTotalAmount() - amount);
            curC.setTotalPrice(curC.getTotalPrice() - p.getPrice()*amount);
        }else{
            curC.setTotalPrice(0);
            curC.setTotalAmount(0);
            curC.setCartItem(new HashSet<>());
        }
            cartDAO.update(curC);
            m.getConfirms().add("cartItem was successfully deleted");
            return m;
    }

}
