package jschool.service.impl;

import jschool.dao.*;
import jschool.dto.OrderDTO;
import jschool.dto.OrderProductDTO;
import jschool.dto.OrderStatusDTO;
import jschool.dto.ShippingDTO;
import jschool.dto.*;

import jschool.helpers.JMSSender;
import jschool.model.*;
import jschool.service.*;
import jschool.validator.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final DTOConverterService dtoConverterService;
    private UserService userService;
    private OrderDAO orderDAO;
    private ShippingTypeDAO shippingTypeDAO;
    private OrderProductDAO orderProductDAO;
    private OrderProductHistoryDAO orderHistoryProductDAO;
    private ProductDAO productDAO;
    private final UserDAO userDAO;
    private final PaymentStatusDAO paymentStatusDAO;
    private final PaymentTypeDAO paymentTypeDAO;
    private final OrderStatusDAO orderStatusDAO;
    private final CartService cartService;
    private final OrderHistoryDAO orderHistoryUserDAO;
    private final OrderAuditDAO orderAuditDAO;
    private final SMSSenderService smsSender;

    @Autowired
    public OrderServiceImpl(DTOConverterService dtoConverterService, UserService userService,
                            OrderDAO orderDAO, ShippingTypeDAO shippingTypeDAO, OrderProductDAO orderProductDAO,
                            OrderProductHistoryDAO orderHistoryProductDAO, ProductDAO productDAO, UserDAO userDAO, PaymentStatusDAO paymentStatusDAO,
                            PaymentTypeDAO paymentTypeDAO, OrderStatusDAO orderStatusDAO,
                            CartService cartService, OrderHistoryDAO orderHistoryUserDAO,
                            OrderAuditDAO orderAuditDAO, SMSSenderService smsSender) {
        this.dtoConverterService = dtoConverterService;
        this.orderHistoryProductDAO = orderHistoryProductDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.paymentStatusDAO = paymentStatusDAO;
        this.paymentTypeDAO = paymentTypeDAO;
        this.orderStatusDAO = orderStatusDAO;
        this.shippingTypeDAO =shippingTypeDAO;
        this.userService = userService;
        this.orderProductDAO = orderProductDAO;
        this.orderDAO = orderDAO;
        this.cartService = cartService;
        this.orderHistoryUserDAO = orderHistoryUserDAO;
        this.orderAuditDAO = orderAuditDAO;
        this.smsSender = smsSender;
    }


    @Override
    public List<Order> listOrders() {
        return  this.orderDAO.list();
    }

    @Override
    public OrderDTO findById(int id) {
        return dtoConverterService.getOrderDTO(this.orderDAO.findById(id));
    }

    @Override
    public OrderFullDTO findHistoryById(int id) {
        OrderHistory orderHistoryUser = orderHistoryUserDAO.findById(id);
        List<OrderAudit> orderAudit = orderAuditDAO.list(id);
        return  dtoConverterService.getFullOrderHistoryDTO(orderAudit,orderHistoryUser);
    }

    @Override
    public List<ShippingDTO> listShippingType() {
        return dtoConverterService.getShippingDTOList(this.shippingTypeDAO.list());
    }

    @Override
    public List<PaymentTypeDTO> listPaymentType() {
        return dtoConverterService.getPaymentTypeDTOList(this.paymentTypeDAO.list());
    }

    @Override
    public void update(Order o) {
        this.orderDAO.update(o);
    }



    /**changes order status to cancelled and returns payment if necessary*/
    @Override
    public Message cancelOrder(OrderDTO o) {
        Message m = new Message();
        try {
            Order order = this.orderDAO.findById(o.getId());
            OrderStatus prevStat = order.getOrderStatus();
            order.setOrderStatus(this.orderStatusDAO.getByStatus("cancelled"));
            if (order.getPaymentStatus().getStatus().equals("paid")) {
                m.getConfirms().add("We sent you refund. Please contanct manager if money did not come.\n manager@imaginarium.com");
                order.setPaymentStatus(this.paymentStatusDAO.getByStatus("not paid"));
            }
            updateProductStorageAmount(o, false);
            this.orderDAO.update(order);
            saveOrderHistory(o,order,prevStat);
            smsSender.send(order.getUser().getPhone(),"Order " + o.getId() +" was cancelled. Your T-Store");
        }catch (Exception e){
            m.getErrors().add("cannot cancel the order");
        }
        return m;
    }


    @Override
    public Message updateOrder(OrderDTO o) {
        Message m = new Message();
        try {
            Order order = this.orderDAO.findById(o.getId());
            OrderStatus prevStat = order.getOrderStatus();

            order.setOrderStatus(this.orderStatusDAO.findById(o.getOrderStatus().getId()));
            order.setPaymentStatus(this.paymentStatusDAO.findById(o.getPaymentStatus().getId()));

            if (o.getOrderStatus().isPaid()){
                order.setPaymentStatus(this.paymentStatusDAO.getByStatus("paid"));
            }
            this.orderDAO.update(order);
            saveOrderHistory(o,order, prevStat);
            smsSender.send(order.getUser().getPhone(),"Order "+o.getId()+" status was changed to " + order.getOrderStatus().getStatus() + ".Your T-Store");
            m.getConfirms().add("We fixed order information, track in cabinet.");
        }catch (Exception e){
            e.printStackTrace();
            m.getErrors().add("cannot update the order. Try again or contact admin@imaginarium.com ");
        }
        return m;
    }

    /**updates product amount in the store when user makes order.*/
    private void updateProductStorageAmount(OrderDTO orderDTO, boolean isToDec){
        List<OrderProductDTO> orderProductDTOList = orderDTO.getOrderProducts();

        Map<Product,Integer> productList = getProductList(orderProductDTOList);
        for(Map.Entry<Product, Integer> productIntegerEntry : productList.entrySet()){
            int amount = isToDec ? productIntegerEntry.getValue() : - productIntegerEntry.getValue();
            Product product = productIntegerEntry.getKey();
//            product.setAmount(product.getAmount() - amount);
            product.setAmount(product.getAmount() - amount < 0 ? 0 : product.getAmount() - amount);
           // this.productDAO.update(product);
        }
    }

    private  Map<Product,Integer> getProductList(List<OrderProductDTO> orderProductDTO){
        Map<Product,Integer> products= new HashMap<>();
        for(OrderProductDTO opDto : orderProductDTO){
            int productid = opDto.getProductid();
            products.put(this.productDAO.findByIdForUpdate(productid),opDto.getAmount());
        }
        return products;
    }

    private OrderAudit saveOrderAudit(OrderDTO orderDTO, Order order, OrderStatus prevStat){
        OrderAudit orderAudit = new OrderAudit();
        orderAudit.setDate(Timestamp.valueOf(LocalDateTime.now()));
        orderAudit.setUser(order.getUser());
        orderAudit.setCurOrderStatus(order.getOrderStatus());
        orderAudit.setOrder(order);
        orderAudit.setCurOrderStatus(order.getOrderStatus());
        orderAudit.setPrevOrderStatus(prevStat);
        orderAudit.setPaid(order.getPaymentStatus().getStatus().equals("paid"));
        return orderAudit;
    }

    private void saveOrderHistory(OrderDTO orderDTO, Order orderNew, OrderStatus prevState){
        OrderHistory orderHistoryUser = orderHistoryUserDAO.findById(orderNew.getId());
        boolean flag = true;
        if (Objects.isNull(orderHistoryUser)){
            flag = false;
            orderHistoryUser = new OrderHistory();
        }
        orderHistoryUser.setAddress(orderNew.getUser().getActiveAddressId().getAddress());
        orderHistoryUser.setClientName(orderNew.getUser().getFullName());
        orderHistoryUser.setUser(orderNew.getUser());
        orderHistoryUser.setDate(orderNew.getDate());
        orderHistoryUser.setEmail(orderNew.getUser().getEmail());
        orderHistoryUser.setOrderStatus(orderNew.getOrderStatus());
        orderHistoryUser.setPaymentStatus(orderNew.getPaymentStatus());
        orderHistoryUser.setPaymentType(orderNew.getPaymentType());
        orderHistoryUser.setTotalPrice(orderNew.getTotalPrice());
        orderHistoryUser.setTotalAmount(orderDTO.getAmount());
        System.out.println("in save order history========");
        System.out.println(orderDTO);
        orderHistoryUser.setShipping(orderNew.getShippingType().getType());

        //if it is a new order history then create orderhistoryproducts objects
        if (!flag){
            LinkedList<OrderProductHistory> orderProductLinkedList = new LinkedList<>();
            for (OrderProduct p : orderNew.getOrderProducts()) {
                OrderProductHistory orderProductHistory = new OrderProductHistory();
                orderProductHistory.setAmount(p.getAmount());
                orderProductHistory.setPrice(p.getPrice());
                orderProductHistory.setProduct(p.getProduct());
                orderProductHistory.setProductName(p.getProduct().getName());
                orderProductHistory.setOrder(orderHistoryUser);
                //
                orderProductLinkedList.add(orderProductHistory);
            }
            orderHistoryUser.setOrderProductHistories(orderProductLinkedList);
        }
        orderHistoryUser.setOrder(orderNew);

       OrderAudit orderAudit = saveOrderAudit(orderDTO,orderNew,prevState);
        if (flag) {
            orderHistoryUserDAO.update(orderHistoryUser);
        }else{
            orderHistoryUserDAO.add(orderHistoryUser);
        }
        orderAuditDAO.add(orderAudit);
    }
    @Override
    public void add(Order c) {
        this.orderDAO.add(c);

    }

    /**checks products amount in store, returns sad message in case its not enought gods.*/
    private boolean checkAmount(Message m,  List<OrderProduct> op,OrderDTO orderDTO){
        for(OrderProductDTO opDto : orderDTO.getOrderProducts()){
            int productid = opDto.getProductid();
            int amount = opDto.getAmount();

            Product product = this.productDAO.findById(productid);
            int storageAmount = product.getAmount();
            if (amount > storageAmount){
                m.getErrors().add(String.format("Sorry, item '%s' is not performed in enough quantity ",product.getName() ));
                return false;
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setAmount(amount);
            orderProduct.setPrice(opDto.getPrice());
            orderProduct.setProduct(product);
            op.add(orderProduct);
        }
        return true;

    }
    @Override
    public Message add(OrderDTO orderDTO) {
        Message message = new Message();
        if (orderDTO.getAmount() <=0) {
            message.getErrors().add("cart is empty");
            return message;
        }
        Order orderNew = new Order();
        orderNew.setDate(new Timestamp(System.currentTimeMillis()));
        orderNew.setTotalPrice(orderDTO.getTotalPrice());
        orderNew.setUser(this.userDAO.findByEmail(orderDTO.getEmail()));
        orderNew.setPaymentType(this.paymentTypeDAO.findByName(orderDTO.getPaymentType().getType()));
        System.out.println("=========-=-" + orderDTO);
        orderNew.setShippingType(this.shippingTypeDAO.find(orderDTO.getShippingType().getType()));

        orderNew.setPaymentStatus(this.paymentStatusDAO.getByStatus("not paid"));
        orderNew.setOrderStatus(this.orderStatusDAO.getByStatus("not paid"));

        List<OrderProduct> op = new LinkedList<>();
        //check
        if (!checkAmount(message,op,orderDTO)){
            return message;
        };

        //reduceAmount
        updateProductStorageAmount(orderDTO, true);

        orderNew.setOrderProducts(op);
        this.orderDAO.add(orderNew);

        for (OrderProduct o : op){
            o.setOrder(orderNew);
            this.orderProductDAO.add(o);
        }

        //OrderHistory
        saveOrderHistory(orderDTO,orderNew,null);
        message.getConfirms().add(String.format("Order %d : %d items, %d total price is successfully saved",
                orderNew.getId(), orderDTO.getAmount(),orderNew.getTotalPrice()));

        orderDTO.setId(orderNew.getId());
        orderDTO.setPaymentStatus(dtoConverterService.getPaymentStatusDTO(orderNew.getPaymentStatus()));
        orderDTO.setOrderStatus(dtoConverterService.getOrderStatusDTO(orderNew.getOrderStatus()));
        JMSSender.send();
        return message;
    }


    @Override
    public Integer getCurUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            User curU = userDAO.findByEmail(auth.getName());
            Collection<? extends GrantedAuthority> authorities
                    = auth.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("USER")) {
                    return curU.getId();
                } else if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("MANAGER")) {
                    return -1;
                }
            }
        }
        return null;
    }

    @Transactional
    public User getCurUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            User curU = userDAO.findByEmail(auth.getName());
            return curU;
        }else return null;
    }


    /**helper method, returns unpaid, paid or delivered orders list depends on type value.*/
    private List<OrderHistoryDTO> getProperList(int type, boolean isAdmin){
        User cUser = getCurUser();
        Integer id =  Objects.isNull(cUser) ? null : cUser.getId();
        if (isAdmin){
            id = getCurUserId();
        }
        List<OrderHistory> orders = new LinkedList<>();
        if (Objects.isNull(id)){
            return null;
        }
        switch (type) {
            case -1: orders = this.orderHistoryUserDAO.getUnpaidOrderList(id);break;
            case 0: orders = this.orderHistoryUserDAO.getPaidOrderList(id);break;
            case 1: orders =this.orderHistoryUserDAO.getProcessedOrderList(id);break;
        }
        List<OrderHistoryDTO> dto = dtoConverterService.getOrderHistoryDTOList(orders);
        return dto;
    }

    @Override
    public List<OrderHistoryDTO> getUnpaidOrderList(boolean isAdmin){
        return getProperList(-1,isAdmin);
    };

    @Override
    public List<OrderHistoryDTO> getPaidOrderList(boolean isAdmin){
        return getProperList(0, isAdmin);
    };

    @Override
    public List<OrderHistoryDTO> getProcessedOrderList(boolean isAdmin) {
        return getProperList(1, isAdmin);
    };


    @Override
    public List<OrderStatus> getNextOrderStatus(OrderStatus status){
        List<OrderStatus> resultlist = new LinkedList<>();
        resultlist.add(status);
        resultlist.add(this.orderStatusDAO.findById(6));
        switch (status.getId()){
            case 1  : resultlist.add(this.orderStatusDAO.findById(2));
                resultlist.add(this.orderStatusDAO.findById(3));
                break;
            case 2: resultlist.add(this.orderStatusDAO.findById(3));break;
            case 3: resultlist.add(this.orderStatusDAO.findById(4));break;
            default: break;
        }
        return resultlist;
    }

    /**builds status graph*/
    @Override
    public LinkedHashMap<OrderStatusDTO, List<OrderStatusDTO>> getStatusGraph(){
        List<OrderStatus> list = this.orderStatusDAO.list();
        LinkedHashMap<OrderStatusDTO,List<OrderStatusDTO>> map = new LinkedHashMap<>();
        for (OrderStatus l : list){
            map.put(dtoConverterService.getOrderStatusDTO(l),dtoConverterService.getOrderStatusDTOList(this.getNextOrderStatus(l)));
        }
        return map;
    }

    @Override
    public CartDTO getCurUserCart(){
        return this.cartService.getCurUserCart();
    }


    /**returns dto for proper cart display*/
    @Override
    public CartExtendedDTO getDTOForCart(){
        return dtoConverterService.getCartExtendedDto(null,
                getCurUserCart(),
                listPaymentType(),
                listShippingType(),
                this.userService.getCurUser());

    }

    /**returns proper dto for  given cart, where anon cart got from cookie*/

    @Override
    public CartExtendedDTO getDTOForCart(String anonCookie){
        User u = this.userService.getCurUser();
        LinkedHashMap anonCart = null;
        CartDTO curC = null;
        if (Objects.isNull(u)) {
            if (!Objects.isNull(anonCookie)){
                anonCart = this.cartService.formAnonymousCartByJSONString(new String(Base64.getDecoder().decode(anonCookie.getBytes())));
            }
        }else{
            curC = getCurUserCart();
        }
        return dtoConverterService.getCartExtendedDto(
                anonCart,
                curC,
                listPaymentType(),
                listShippingType(),
                u);
    }

    @Override
    public CategorizedOrdersDTO getCategorizedOrdersDTO(boolean isAdmin){
        return dtoConverterService.getCategorizedOrdersDTO(
                this.getUnpaidOrderList(isAdmin),
                this.getPaidOrderList(isAdmin),
                this.getProcessedOrderList(isAdmin),
                this.getCurUserId());
    }

}
