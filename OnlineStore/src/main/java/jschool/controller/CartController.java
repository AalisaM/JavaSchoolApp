package jschool.controller;

import jschool.dto.OrderDTO;
import jschool.dto.OrderHistoryDTO;
import jschool.service.MailSender;
import jschool.service.*;
import jschool.validator.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Controller is responsible for cart page,
 * creating and approving orders by users.
 * Did not moved orders in another controller because it strongly tied with carts.
 * */
@Controller
public class CartController {
    private MailSender mailService;
    private static final Logger log = Logger.getLogger(CartController.class);
    private OrderService orderService;
    private CartService cartService;
    private CartItemService cartItemService;
    private String ipAddr;

    @Autowired
    CartController(MailSender mailService, OrderService orderService, CartService cartService, CartItemService cartItemService, String ipAddr){
        this.mailService = mailService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.ipAddr = ipAddr;
    }

    /**
     * This method processes get cart page request
     * @param fooCookie cookie with cart data of anon user
     * @param model model for rendering
     * @return cart page
     */
    @GetMapping(value = "/cart")
    public String currentUserCart(@CookieValue(value = "cartItem", required = false) String fooCookie, Model model) {
        log.info(this.orderService.getDTOForCart(fooCookie));
        model.addAttribute("cartExtendedDTO", this.orderService.getDTOForCart(fooCookie));
        model.addAttribute("ip", this.ipAddr);
        return "cart/cart";
    }

    /**
     * This method processes make order request
     * @param p orderdto to make order
     * @param model model for rendering
     * @return
     */
    @PostMapping(value= "/cart/makeOrder")
    @ResponseBody
    public ModelAndView makeOrder(@RequestBody OrderDTO p, ModelMap model){
        log.info("in  make order;");
        Message message = this.orderService.add(p);
        model.addAttribute("message", message);
        model.addAttribute("ip", this.ipAddr);
        if (!message.getErrors().isEmpty()){
            model.addAttribute("cartExtendedDTO", this.orderService.getDTOForCart());
            return new ModelAndView("cart/cart", model);
        }else{
            model.addAttribute("orderdto",p);
            return new ModelAndView("order/orderApproval", model);
        }
    }

    /**
     * This method processes cancel order request
     * @param p
     * @param model model for rendering
     * @return proper page
     */
    @PostMapping(value= "/cart/cancelOrder")
    public String cancelOrder(@RequestBody OrderDTO p, ModelMap model){
        log.info("CANCEL" + p.toString());
        Message message = this.orderService.cancelOrder(p);
        mailService.sendOrderMail(p);
        model.addAttribute("message", message);
        return "redirect:/cart";
    }

    /**
     *  This method processes edit order request
     * @param p orderdto to edit
     * @param model model for rendering
     * @param redir atributes for redirecting
     * @return redirect to proper page
     */
    @PostMapping(value= "/cart/editOrder")
    public String approveOrder(@RequestBody OrderDTO p, Model model, RedirectAttributes redir){
        log.info("EDIT" + p.toString());
        Message message = this.orderService.updateOrder(p);
        mailService.sendOrderMail(p);
        redir.addFlashAttribute("message", message);
        return "redirect:/";
    }

    /**
     * This method processes get orders list page
     * @param fooCookie cookie with cart data of anon user
     * @param model model for rendering
     * @return orders list page
     */
    @GetMapping(value = "/orders")
    public String ordersUserList(@CookieValue(value = "cartItem", required = false) String fooCookie,Model model) {
        Integer id = this.orderService.getCurUserId();
        model.addAttribute("ip", this.ipAddr);
        if (!Objects.isNull(id)) {
            model.addAttribute("categorizedOrders", this.orderService.getCategorizedOrdersDTO(true));

            if (id < 0){
                model.addAttribute("nextOrderStatus", this.orderService.getStatusGraph());
            }
            return "order/orderTableTemplate";
        }else {
            Message m = new Message();
            try {
                model.addAttribute("cartAnon", this.cartService.formAnonymousCartByJSONString(new String(Base64.getDecoder().decode(fooCookie.getBytes()))));
                m.getErrors().add("please login first");
            }catch (Exception e){
                log.error(e.toString());
                m.getErrors().add("as anonymous user you have no orders. Please collect cart and login.");
            }
            model.addAttribute("message", m);
            return "cart/cart";
        }
    }

    /**
     * This method processes user's request to get order page
     * @param model model for rendering
     * @return order page for user
     */
    @PostMapping(value = "/userOrder", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView userOrder(ModelMap model) {
        Integer id = this.orderService.getCurUserId();
        model.addAttribute("ip", this.ipAddr);

        if (!Objects.isNull(id)) {
            model.addAttribute("categorizedOrders", this.orderService.getCategorizedOrdersDTO(false));
        }
        return new ModelAndView("user/userOrdersLists",model);
    }

    /**
     * This method processes paid orders data request
     * @param model model for rendering
     * @return page with paid orders
     */
    @PostMapping(value = "/paidorders", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView paidOrdersList(ModelMap model) {
        Integer id = this.orderService.getCurUserId();
        model.addAttribute("ip", this.ipAddr);
        if (!Objects.isNull(id)) {
            model.addAttribute("orders", this.orderService.getCategorizedOrdersDTO(true).getPaidOrders());
            if (id < 0){
                model.addAttribute("nextOrderStatus", this.orderService.getStatusGraph());
            }
        }
        return new ModelAndView("order/orderTableTemplate",model);
    }

    /**
     * This method processes unpaid orders data request
     * @param model model for rendering
     * @return page with unpaid orders
     */
    @PostMapping(value = "/unpaidorders", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView unpaidOrdersList(ModelMap model) {
        Integer id = this.orderService.getCurUserId();
        model.addAttribute("ip", this.ipAddr);
        if (!Objects.isNull(id)) {
            List<OrderHistoryDTO> ordersDto =this.orderService.getCategorizedOrdersDTO(true).getUnpaidOrders();
            model.addAttribute("orders",ordersDto);
            if (id < 0){
                model.addAttribute("nextOrderStatus", this.orderService.getStatusGraph());
            }
        }
        return  new ModelAndView("order/orderTableTemplate",model);
    }

    /**
     * This method processes delivered orders data request
     * @param model model for rendering
     * @return page with delivered orders
     */
    @PostMapping(value = "/deliveredorders", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView processedOrdersList(ModelMap model) {
        Integer id = this.orderService.getCurUserId();
        model.addAttribute("ip", this.ipAddr);
        if (!Objects.isNull(id)) {
            model.addAttribute("orders", this.orderService.getCategorizedOrdersDTO(true).getProcessedOrders());
            if (id < 0){
                model.addAttribute("nextOrderStatus", this.orderService.getStatusGraph());
            }
        }
        return  new ModelAndView("order/orderTableTemplate",model);
    }

    /**
     * This method processes add product to cart request
     * @param json json input with product
     * @param model  model for rendering
     * @return carticon page
     */
    @PostMapping(value= "/cart/addToCart", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addToCart(@RequestBody String json, ModelMap model) {
        Message m = new Message();
        cartItemService.addProductToCart(m, json);
        model.addAttribute("message", m);
        model.addAttribute("curCart", this.cartService.getCurUserCart());
        return new ModelAndView("cart/cartIcon", model);
    }

    /**
     * This method processes remove cartitem from cart request
     * @param json json input with cartitem data
     * @param model model for rendering
     * @return carticon page
     */
    @PostMapping(value= "/cart/removeFromCart", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView removeFromCart(@RequestBody String json, ModelMap model) {
        Message m = new Message();
        cartItemService.removeProductFromCart(m, json);
        model.addAttribute("message", m);
        model.addAttribute("curCart", this.cartService.getCurUserCart());
        return new ModelAndView("cart/cartIcon", model);
    }

    /**
     * This method processes anon cart to return normal object
     * @param fooCookie cookie with cart data of anon user
     * @param model  model for rendering
     * @return  carttemplate page with processed anon cart data
     */
    @PostMapping(value= "/cart/processAnonymousCart", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView showCartPage(@CookieValue(value = "cartItem", required = false) String fooCookie, ModelMap model){
        model.addAttribute("cartExtendedDTO", this.orderService.getDTOForCart(fooCookie));
        return new ModelAndView("cart/cartData", model);
    }

    /**
     * This method processes clear cart request
     * @param model model for rendering
     * @return carticon template
     */
    @GetMapping(value = "/clearCart", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView  clearCart(ModelMap model){
        this.cartService.clearCartForCurUser();
        model.addAttribute("curCart", this.cartService.getCurUserCart());
        return new ModelAndView("cart/cartIcon", model);
    }

}
