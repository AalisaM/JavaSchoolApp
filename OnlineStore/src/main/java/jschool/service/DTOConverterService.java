package jschool.service;

import jschool.dto.*;
import jschool.model.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * DTO to Entity and Entity to DTO converter service interface
 */
public interface DTOConverterService {
    /**
     * Get object with all cart info
     * @param anon anonymous user cart
     * @param cur current logged user cart
     * @param p payment types
     * @param s shiping types
     * @param u logged user
     * @return object for rendering cart page
     */
    CartExtendedDTO getCartExtendedDto(LinkedHashMap<String,Object> anon, CartDTO cur, List<PaymentTypeDTO> p,
                                       List<ShippingDTO> s, User u);

    /**
     * Get DTO from User entity
     * @param u User entity
     * @return DTO
     */
    UserDTO getUserDTO(User u);

    /**
     * Get List of DTOs from list of enties
     * @param u list of entities
     * @return list of dtos
     */
    List<UserDTO> getUserDTOList(List<User> u);

    /**
     * Get DTO from OrderAudit entity
     * @param u OrderAudit entity
     * @return DTO
     */
    OrderAuditDTO getOrderAuditDTO(OrderAudit u);

    /**
     * Get List of DTOs from list of enties
     * @param u list of entities
     * @return list of dtos
     */
    List<OrderAuditDTO> getOrderAuditDTOList(List<OrderAudit> u);

    /**
     * Get DTO for order history and order audit entities
     * @param u OrderAudit entity list
     * @param o OrderHistory enity
     * @return DTO combination
     */
    OrderFullDTO getFullOrderHistoryDTO(List<OrderAudit> u, OrderHistory o);

    /**
     * Get DTO from OrderProduct entity
     * @param o OrderProduct entity
     * @return DTO
     */
    OrderProductDTO getOrderProductDTO(OrderProduct o);

    /**
     * Get List of DTOs from list of enties
     * @param o list of entities
     * @return list of dtos
     */
    Set<OrderProductDTO> getOrderProductDTOList(Set<OrderProduct> o);

    /**
     * Get DTO from Order entity
     * @param o Order entity
     * @return DTO
     */
    OrderDTO getOrderDTO(Order o);

    /**
     * Get DTO from OrderHistory entity
     * @param orderHistory OrderHistory entity
     * @return DTO
     */
    OrderHistoryDTO getOrderHistoryDTO(OrderHistory orderHistory);

    /**
     * Get List of DTOs from list of enties
     * @param o list of entities
     * @return list of dtos
     */
    List<OrderHistoryDTO> getOrderHistoryDTOList(List<OrderHistory> o);

    /**
     * Get List of DTOs from list of enties
     * @param o list of entities
     * @return list of dtos
     */
    List<OrderDTO> getOrderDTOList(List<Order> o);

    /**
     * Get DTO from Cart entity
     * @param c Cart entity
     * @return DTO
     */
    CartDTO getCartDTO(Cart c);

    /**
     * Get List of DTOs from set of enties
     * @param list list of entities
     * @return list of dtos
     */
    List<CartItemDTO> getCartItemDTOList(Set<CartItem> list);

    /**
     * Get DTO from Address entity
     * @param a Address entity
     * @return DTO
     */
    AddressDTO getAddressDTO(Address a);

    /**
     * Get set of DTOs from set of enties
     * @param a list of entities
     * @return set of dtos
     */
    Set<AddressDTO> getAddressDTOList(Set<Address> a);

    /**
     * Get DTO from PaymentType entity
     * @param p PaymentType entity
     * @return DTO
     */
    PaymentTypeDTO getPaymentTypeDTO(PaymentType p);

    /**
     * Get List of DTOs from list of enties
     * @param list list of entities
     * @return list of dtos
     */
    List<PaymentTypeDTO> getPaymentTypeDTOList(List<PaymentType> list);

    /**
     * Get DTO from ShippingType entity
     * @param s ShippingType entity
     * @return DTO
     */
    ShippingDTO getShippingDTO(ShippingType s);

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return list of dtos
     */
    List<ShippingDTO> getShippingDTOList(List<ShippingType> a);

    /**
     * Get DTO from PaymentStatus entity
     * @param p PaymentStatus entity
     * @return DTO
     */
    PaymentStatusDTO getPaymentStatusDTO(PaymentStatus p);

    /**
     * Get DTO from Product entity
     * @param p Product entity
     * @return DTO
     */
    ProductDTO getProductDTO(Product p);


    /**
     * Returns dto object for second app
     * @param p product entity
     * @return dto
     */
    ProductRestDTO getProductRestDTO(Product p);

    /**
     * Get product entity from dto
     * */
    Product getProductFromDTO(Product product, ProductDTO productDTO);

    /**
     * Get DTO from Product entity
     * @param p Product entity
     * @return DTO
     */
    ProductRawDTO getProductRawDTO(Product p);

    /**
     * Get DTO without foreign lists from Category entity
     * @param c User Category entity
     * @return DTO
     */
    CategoryRawDTO getCategoryRAWDTO(Category c);

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return list of dtos
     */
    List<CategoryRawDTO> getCategoryRAWDTOList(List<Category> a);

    /**
     * Get Set of DTOs from collection of enties
     * @param set list of entities
     * @return set of dtos
     */
    Set<ProductDTO> getProductDTOList(Collection<Product> set);

    /**
     * Get Set of DTOs from collection of enties
     * @param set list of entities
     * @return set of dtos
     */
    Set<ProductRawDTO> getProductRawDTOList(Collection<Product> set);


    /**
     * Get DTO from CartItem entity
     * @param c CartItem entity
     * @return DTO
     */
    CartItemDTO getCartItemDTO(CartItem c);


    /**
     * Get DTO from Category entity
     * @param c Category entity
     * @return DTO
     */
    CategoryDTO getCategoryDTO(Category c);

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return set of dtos
     */
    List<CategoryDTO> getCategoryDTOList(List<Category> a);

    /**
     * Get DTO from OrderStatus entity
     * @param status OrderStatus entity
     * @return DTO
     */
    OrderStatusDTO getOrderStatusDTO(OrderStatus status);

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return set of dtos
     */
    List<OrderStatusDTO> getOrderStatusDTOList(List<OrderStatus> a);

    /**
     * Get DTO with orders categorised by order status
     * @param unpaidOrders unpaid orders
     * @param paidOrders paid orders
     * @param processedOrders delivered orders
     * @param userRoleId user role id
     * @return DTO combination of all orders
     */
    CategorizedOrdersDTO getCategorizedOrdersDTO(List<OrderHistoryDTO> unpaidOrders,
                                                 List<OrderHistoryDTO> paidOrders,
                                                 List<OrderHistoryDTO> processedOrders,
                                                 Integer userRoleId);
}
