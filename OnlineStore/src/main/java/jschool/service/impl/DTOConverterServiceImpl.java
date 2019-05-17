package jschool.service.impl;

import jschool.dto.*;
import jschool.model.*;
import jschool.service.DTOConverterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This service is used to convert object to dto for proper data manipulations in controller-service link.
 * */

@Service
public class DTOConverterServiceImpl implements DTOConverterService {

    private final ModelMapper modelMapper;

    @Autowired
    public DTOConverterServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Get object with all cart info
     * @param anon anonymous user cart
     * @param cur current logged user cart
     * @param p payment types
     * @param s shiping types
     * @param u logged user
     * @return object for rendering cart page
     */
    @Override
    public CartExtendedDTO getCartExtendedDto(LinkedHashMap<String,Object> anon, CartDTO cur, List<PaymentTypeDTO> p,
                                              List<ShippingDTO> s, User u){
       CartExtendedDTO dto = new CartExtendedDTO();
       dto.setCartAnon(anon);
       dto.setCurCart(cur);
       dto.setPaymentType(p);
       dto.setShippingType(s);
       dto.setUser(getUserDTO(u));
       return dto;
    }

    /**
     * Get DTO from User entity
     * @param u User entity
     * @return DTO
     */
    @Override
    public UserDTO getUserDTO(User u){
        if (Objects.isNull(u)) return null;
        UserDTO dto = new UserDTO();
        dto.setActiveAddressId(getAddressDTO(u.getActiveAddressId()));
        dto.setAddresses(getAddressDTOList(u.getAddresses()));
        dto.setBirth(u.getBirth());
        dto.setEmail(u.getEmail());
        dto.setFullName(u.getFullName());
        dto.setPassword(u.getPassword());
        dto.setAdmin(u.isAdmin());
        dto.setId(u.getId());
        dto.setPhone(u.getPhone());
        return dto;
    }

    /**
     * Get List of DTOs from list of enties
     * @param u list of entities
     * @return list of dtos
     */
    @Override
    public List<UserDTO> getUserDTOList(List<User> u){
        List<UserDTO> dto = new LinkedList<>();
        for(User ci : u){
            dto.add(getUserDTO(ci));
        }
        return dto;
    }

    /**
     * Get DTO from OrderAudit entity
     * @param u OrderAudit entity
     * @return DTO
     */
    @Override
    public OrderAuditDTO getOrderAuditDTO(OrderAudit u){
        if (Objects.isNull(u)) return null;
        OrderAuditDTO orderAuditDTO = new OrderAuditDTO();

        String cur = Objects.isNull(u.getCurOrderStatus()) ? "" :u.getCurOrderStatus().getStatus();
        String prev = Objects.isNull(u.getPrevOrderStatus()) ? "" :u.getPrevOrderStatus().getStatus();

        orderAuditDTO.setCurOrderStatus(cur);
        orderAuditDTO.setPrevOrderStatus(prev);
        orderAuditDTO.setDate(u.getDate());
        orderAuditDTO.setManager(u.getUser().getFullName());
        orderAuditDTO.setPaid(u.isPaid());
        orderAuditDTO.setId(u.getId());
        return orderAuditDTO;
    }

    /**
     * Get List of DTOs from list of enties
     * @param u list of entities
     * @return list of dtos
     */
    @Override
    public List<OrderAuditDTO> getOrderAuditDTOList(List<OrderAudit> u){
        List<OrderAuditDTO> dto = new LinkedList<>();
        for(OrderAudit ci : u){
            dto.add(getOrderAuditDTO(ci));
        }
        return dto;
    }

    /**
     * Get DTO for order history and order audit entities
     * @param u OrderAudit entity list
     * @param o OrderHistory enity
     * @return DTO combination
     */
    @Override
    public OrderFullDTO getFullOrderHistoryDTO(List<OrderAudit> u, OrderHistory o){
        if (Objects.isNull(u)) return null;
        OrderFullDTO orderFullDTO = new OrderFullDTO();
        List<OrderAuditDTO> od = new LinkedList<>();
        for (OrderAudit oa: u){
            od.add(getOrderAuditDTO(oa));
        }
        orderFullDTO.setOrderAuditDTO(od);
        orderFullDTO.setOrderHistoryDTO(getOrderHistoryDTO(o));
        return orderFullDTO;
    }


    /**
     * Get DTO from OrderProduct entity
     * @param o OrderProduct entity
     * @return DTO
     */
    @Override
    public OrderProductDTO getOrderProductDTO(OrderProduct o){
        if (Objects.isNull(o)) return null;
        OrderProductDTO dto = new OrderProductDTO();
        dto.setProductName(o.getProduct().getName());
        dto.setProductid(o.getProduct().getId());
        dto.setPrice(o.getPrice());
        dto.setAmount(o.getAmount());
        return dto;
    }

    /**
     * Get List of DTOs from list of enties
     * @param o list of entities
     * @return list of dtos
     */
    @Override
    public Set<OrderProductDTO> getOrderProductDTOList(Set<OrderProduct> o){
        Set<OrderProductDTO> dto = new HashSet<>();
        for(OrderProduct ci : o){
            dto.add(getOrderProductDTO(ci));
        }
        return dto;
    }

    /**
     * Get DTO from Order entity
     * @param o Order entity
     * @return DTO
     */
    @Override
    public OrderDTO getOrderDTO(Order o){
        if (Objects.isNull(o)) return null;
        OrderDTO odto = new OrderDTO();
        Address a = o.getUser().getActiveAddressId();
        if (!Objects.isNull(a)){
            odto.setAddress(a.getAddress());
        }
        odto.setAmount(odto.getAmount());
        odto.setEmail(o.getUser().getEmail());
        odto.setOrderStatus(getOrderStatusDTO(o.getOrderStatus()));
        odto.setPaymentStatus(getPaymentStatusDTO(o.getPaymentStatus()));
        odto.setShippingType(getShippingDTO(o.getShippingType()));
        odto.setPaymentType(getPaymentTypeDTO(o.getPaymentType()));
        odto.setId(o.getId());
        List<OrderProductDTO> op = new LinkedList<>();
        for (OrderProduct p: o.getOrderProducts()){
            OrderProductDTO opdto = new OrderProductDTO();
            opdto.setAmount(p.getAmount());
            opdto.setPrice(p.getPrice());
            opdto.setProductid(p.getProduct().getId());
            opdto.setProductName(p.getProduct().getName());
            odto.setTotalPrice(p.getPrice() + odto.getTotalPrice());
            odto.setAmount(p.getAmount() + odto.getAmount());
            op.add(opdto);
        }
        odto.setOrderProducts(op);
        return odto;
     }

    /**
     * Get DTO from OrderHistory entity
     * @param o OrderHistory entity
     * @return DTO
     */
    @Override
    public OrderHistoryDTO getOrderHistoryDTO(OrderHistory o){
        if (Objects.isNull(o)) return null;
        OrderHistoryDTO odto = new OrderHistoryDTO();
        odto.setAddress(o.getAddress());
        odto.setEmail(o.getEmail());
        odto.setOrderStatus(getOrderStatusDTO(o.getOrderStatus()));
        odto.setPaymentStatus(getPaymentStatusDTO(o.getPaymentStatus()));
        odto.setShippingType(o.getShipping());
        odto.setPaymentType(getPaymentTypeDTO(o.getPaymentType()));
        odto.setId(o.getOrderId());
        odto.setTotalPrice(o.getTotalPrice());
        List<OrderProductDTO> op = new LinkedList<>();
        for (OrderProductHistory p: o.getOrderProductHistories()){
            OrderProductDTO opdto = new OrderProductDTO();
            opdto.setAmount(p.getAmount());
            opdto.setPrice(p.getPrice());
            opdto.setProductid(p.getProduct().getId());
            opdto.setProductName(p.getProductName());
            odto.setAmount(p.getAmount() + odto.getAmount());
            op.add(opdto);
        }
        odto.setOrderProducts(op);
        return odto;
    }

    /**
     * Get List of DTOs from list of enties
     * @param o list of entities
     * @return list of dtos
     */
    @Override
    public  List<OrderHistoryDTO> getOrderHistoryDTOList(List<OrderHistory> o){
        List<OrderHistoryDTO> dto = new LinkedList<>();
        for(OrderHistory ci : o){
            dto.add(getOrderHistoryDTO(ci));
        }
        return dto;
    }

    /**
     * Get List of DTOs from list of enties
     * @param o list of entities
     * @return list of dtos
     */
    @Override
    public  List<OrderDTO> getOrderDTOList(List<Order> o){
        List<OrderDTO> dto = new LinkedList<>();
        for(Order ci : o){
            dto.add(getOrderDTO(ci));
        }
        return dto;
    }

    /**
     * Get DTO from Cart entity
     * @param c Cart entity
     * @return DTO
     */
    @Override
    public CartDTO getCartDTO(Cart c){
        if (Objects.isNull(c)) return null;
        CartDTO dto = new CartDTO();
        dto.setCartItem(getCartItemDTOList(c.getCartItem()));
        dto.setTotalAmount(c.getTotalAmount());
        dto.setTotalPrice(c.getTotalPrice());
        dto.setUser(getUserDTO(c.getUser()));
        dto.setUser_id(c.getUserId());
        return dto;
    }

    /**
     * Get List of DTOs from set of enties
     * @param list list of entities
     * @return list of dtos
     */
    @Override
    public List<CartItemDTO> getCartItemDTOList(Set<CartItem> list){
        List<CartItemDTO> dto = new LinkedList<>();
        for(CartItem ci : list){
            dto.add(getCartItemDTO(ci));
        }
        return dto;
    }

    /**
     * Get DTO from Address entity
     * @param a Address entity
     * @return DTO
     */
    @Override
    public AddressDTO getAddressDTO(Address a){
        if (Objects.isNull(a)) return null;
        return modelMapper.map(a, AddressDTO.class);
    }

    /**
     * Get set of DTOs from set of enties
     * @param a list of entities
     * @return set of dtos
     */
    @Override
    public Set<AddressDTO> getAddressDTOList(Set<Address> a){
        Set<AddressDTO> dto = new HashSet<>();
        for(Address address : a){
            dto.add(getAddressDTO(address));
        }
        return  dto;
    }

    /**
     * Get DTO from PaymentType entity
     * @param p PaymentType entity
     * @return DTO
     */
    @Override
    public PaymentTypeDTO getPaymentTypeDTO(PaymentType p){
        if (Objects.isNull(p)) return null;
        return modelMapper.map(p, PaymentTypeDTO.class);
    }

    /**
     * Get List of DTOs from list of enties
     * @param list list of entities
     * @return list of dtos
     */
    @Override
    public List<PaymentTypeDTO> getPaymentTypeDTOList(List<PaymentType> list){
        List<PaymentTypeDTO> dto = new LinkedList<>();
        for(PaymentType p : list){
            dto.add(getPaymentTypeDTO(p));
        }
        return dto;
    }

    /**
     * Get DTO from ShippingType entity
     * @param s ShippingType entity
     * @return DTO
     */
    @Override
    public ShippingDTO getShippingDTO(ShippingType s){
        if (Objects.isNull(s)) return null;
        return modelMapper.map(s, ShippingDTO.class);
    }

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return list of dtos
     */
    @Override
    public List<ShippingDTO> getShippingDTOList(List<ShippingType> a){
        List<ShippingDTO> dto = new LinkedList<>();
        for(ShippingType type : a){
            dto.add(getShippingDTO(type));
        }
        return  dto;
    }

    /**
     * Get DTO from PaymentStatus entity
     * @param p PaymentStatus entity
     * @return DTO
     */
    @Override
    public PaymentStatusDTO getPaymentStatusDTO(PaymentStatus p){
        if (Objects.isNull(p)) return null;
        return modelMapper.map(p, PaymentStatusDTO.class);
    }

    /**
     * Get DTO from Product entity
     * @param p Product entity
     * @return DTO
     */
    @Override
    public ProductDTO getProductDTO(Product p){
        if (Objects.isNull(p)) return null;
        ProductDTO dto = new ProductDTO();
        dto.setAmount(p.getAmount());
        dto.setDescription(p.getDescription());
        dto.setWeight(p.getWeight());
        dto.setVolume(p.getVolume());
        dto.setPrice(p.getPrice());
        dto.setName(p.getName());
        dto.setMinPlayerAmount(p.getMinPlayerAmount());
        dto.setMaxPlayerAmount(p.getMaxPlayerAmount());
        dto.setId(p.getId());
        dto.setRule(p.getRule());
        dto.setCartItem(this.getCartItemDTOList(p.getCartItem()));
        dto.setOrderProducts(this.getOrderProductDTOList(p.getOrderProducts()));
        dto.setImageSource(p.getImageSource());
        dto.setCategory(this.getCategoryRAWDTO(p.getCategory()));
        dto.setDeleted(p.getDeleted());
        //ProductDTO productDTO = modelMapper.map(p, ProductDTO.class);
       // productDTO.setUploadFile(null);
        return dto;
    }

    /**
     * Returns dto object for second app
     * @param p product entity
     * @return dto
     */
    @Override
    public ProductRestDTO getProductRestDTO(Product p){
        if (Objects.isNull(p)) return null;
        ProductRestDTO dto = new ProductRestDTO();
        dto.setId(p.getId());
        dto.setDescription(p.getDescription());
        dto.setImageSource(p.getImageSource());
        dto.setPrice(p.getPrice());
        dto.setName(p.getName());
        return dto;
    }

    /**
     * Get entity from DTO
     * @param productDTO dto
     * @return entity without categories
     */
    @Override
    public Product getProductFromDTO(Product product, ProductDTO productDTO) {
        product.setDescription(productDTO.getDescription());
        product.setWeight(productDTO.getWeight());
        product.setVolume(productDTO.getVolume());
        product.setName(productDTO.getName());
        product.setMinPlayerAmount(productDTO.getMinPlayerAmount());
        product.setMaxPlayerAmount(productDTO.getMaxPlayerAmount());
        return product;
    }

    /**
     * Get DTO from Product entity
     * @param p Product entity
     * @return DTO
     */
    @Override
    public ProductRawDTO getProductRawDTO(Product p){
        if (Objects.isNull(p)) return null;
        ProductRawDTO dto = new ProductRawDTO();
        dto.setAmount(p.getAmount());
        dto.setDescription(p.getDescription());
        dto.setWeight(p.getWeight());
        dto.setVolume(p.getVolume());
        dto.setPrice(p.getPrice());
        dto.setName(p.getName());
        dto.setMinPlayerAmount(p.getMinPlayerAmount());
        dto.setMaxPlayerAmount(p.getMaxPlayerAmount());
        dto.setId(p.getId());
        dto.setDeleted(p.getDeleted());
        dto.setImageSource(p.getImageSource());
        return dto;
    }

    /**
     * Get DTO without foreign lists from Category entity
     * @param c User Category entity
     * @return DTO
     */
    @Override
    public CategoryRawDTO getCategoryRAWDTO(Category c){
        if (Objects.isNull(c)) return null;
        CategoryRawDTO dto = new CategoryRawDTO();
        dto.setId(c.getId());
        dto.setTitle(c.getName());
        dto.setParentId(c.getParentId());
        dto.setProductAmount(c.getProducts().size());
        dto.setDeleted(c.getDeleted());
        return dto;
    }

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return list of dtos
     */
    @Override
    public List<CategoryRawDTO> getCategoryRAWDTOList(List<Category> a){
        List<CategoryRawDTO> dto = new LinkedList<>();
        for(Category type : a){
            dto.add(getCategoryRAWDTO(type));
        }
        return  dto;
    }

    /**
     * Get Set of DTOs from collection of enties
     * @param set list of entities
     * @return set of dtos
     */
    @Override
    public Set<ProductDTO> getProductDTOList(Collection<Product> set){
        Set<ProductDTO> dto = new HashSet<>();
        for(Product p: set){
            dto.add(getProductDTO(p));
        }
        return dto;
    }

    /**
     * Get Set of DTOs from collection of enties
     * @param set list of entities
     * @return set of dtos
     */
    @Override
    public Set<ProductRawDTO> getProductRawDTOList(Collection<Product> set){
        Set<ProductRawDTO> dto = new HashSet<>();
        for(Product p: set){
            dto.add(getProductRawDTO(p));
        }
        return dto;
    }

    /**
     * Get DTO from CartItem entity
     * @param c CartItem entity
     * @return DTO
     */
    @Override
    public CartItemDTO getCartItemDTO(CartItem c){
        if (Objects.isNull(c)) return null;
        CartItemDTO ci = new CartItemDTO();
        ci.setProduct(getProductRawDTO(c.getProduct()));
        ci.setAmount(c.getAmount());
        ci.setPrice(c.getPrice());
        ci.setId(c.getId());
        return ci;
    }


    /**
     * Get DTO from Category entity
     * @param c Category entity
     * @return DTO
     */
    @Override
    public CategoryDTO getCategoryDTO(Category c){
        if (Objects.isNull(c)) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setProducts(getProductRawDTOList(c.getProducts()));
        dto.setParentId(c.getParentId());
        dto.setDeleted(c.getDeleted());
        return dto;
    }


    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return set of dtos
     */
    @Override
    public List<CategoryDTO> getCategoryDTOList(List<Category> a){
        List<CategoryDTO> dto = new LinkedList<>();
        for(Category type : a){
            dto.add(getCategoryDTO(type));
        }
        return  dto;
    }

    /**
     * Get DTO from OrderStatus entity
     * @param status OrderStatus entity
     * @return DTO
     */
    @Override
    public OrderStatusDTO getOrderStatusDTO(OrderStatus status){
        if (Objects.isNull(status)) return null;
        return modelMapper.map(status, OrderStatusDTO.class);
    }

    /**
     * Get List of DTOs from list of enties
     * @param a list of entities
     * @return set of dtos
     */
    @Override
    public List<OrderStatusDTO> getOrderStatusDTOList(List<OrderStatus> a){
        List<OrderStatusDTO> dto = new LinkedList<>();
        for(OrderStatus type : a){
            dto.add(getOrderStatusDTO(type));
        }
        return  dto;
    }

    /**
     * Get DTO with orders categorised by order status
     * @param unpaidOrders unpaid orders
     * @param paidOrders paid orders
     * @param processedOrders delivered orders
     * @param userRoleId user role id
     * @return DTO combination of all orders
     */
    @Override
    public CategorizedOrdersDTO getCategorizedOrdersDTO(List<OrderHistoryDTO> unpaidOrders,
                                                        List<OrderHistoryDTO> paidOrders,
                                                        List<OrderHistoryDTO> processedOrders,
                                                        Integer userRoleId){
        CategorizedOrdersDTO dto = new CategorizedOrdersDTO();
        dto.setPaidOrders(paidOrders);
        dto.setUnpaidOrders(unpaidOrders);
        dto.setProcessedOrders(processedOrders);
        dto.setUserRoleId(userRoleId);
        return dto;
    }

}
