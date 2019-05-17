package jschool.service;

import jschool.dto.*;

import jschool.model.*;
import jschool.validator.Message;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Order Service Interface
 */
public interface OrderService {

    /**
     * Get list of order entities from db
     * @return list
     */
    List<Order> listOrders();

    /**
     * Get specific order dto by id
     * @param id id
     * @return dto
     */
    OrderDTO findById(int id);

    /**
     * get full order history (audit + order history) by order id
     * @param id order id
     * @return full dto
     */
    OrderFullDTO findHistoryById(int id);

    /**
     * get list of shipping types dtos from db
     * @return list
     */
    List<ShippingDTO> listShippingType();

    /**
     * get list of payment types dtos from db
     * @return
     */
    List<PaymentTypeDTO> listPaymentType();

    /**
     * get cart for currently logged user
     * @return cart dto
     */
    CartDTO getCurUserCart();

    /**
     * update order entity in db
     * @param o entity
     */
    void update(Order o);

    /**
     * add order entity
     * @param c order
     */
    void add(Order c);

    /**
     * add order by orderdto
     * @param c dto
     * @return Message object info
     */
    Message add(OrderDTO c);

    /**
     * get id of currently logged user
     * @return id
     */
    Integer getCurUserId();

    /**
     * Changes order status to cancelled
     * @param o dto
     * @return Message object info
     */
    Message cancelOrder(OrderDTO o);

    /**
     * Updates order information by dto
     * @param o dto
     * @return Message object info
     */
    Message updateOrder(OrderDTO o);

    /**
     * get list of unpaid order histories
     * @param isAdmin current user is admin
     * @return list
     */
    List<OrderHistoryDTO> getUnpaidOrderList(boolean isAdmin);

    /**
     *  get list of paid order histories
     * @param isAdmin current user is admin
     * @return list
     */
    List<OrderHistoryDTO> getPaidOrderList(boolean isAdmin);

    /**
     *  get list of processed order histories
     * @param isAdmin current user is admin
     * @return list
     */
    List<OrderHistoryDTO> getProcessedOrderList(boolean isAdmin);

    /**
     * get list of possible next order statuses for current status
     * @param status status
     * @return list
     */
    List<OrderStatus> getNextOrderStatus(OrderStatus status);

    /**
     * get map status -> next possible statuses
     * @return map
     */
    LinkedHashMap<OrderStatusDTO, List<OrderStatusDTO>> getStatusGraph();

    /**
     * get full cart info for current user cart
     * @return cart full info
     */
    CartExtendedDTO getDTOForCart();

    /**
     * get full cart info for anon user
     * @param anonCookie cookie with anon cart data
     * @return full cart info
     */
    CartExtendedDTO getDTOForCart(String anonCookie);

    /**
     * get full orders list grouped by statuses
     * @param isAdmin if cur user is admin
     * @return orders list
     */
    CategorizedOrdersDTO getCategorizedOrdersDTO(boolean isAdmin);

}
