package jschool.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class CartExtendedDTO {
    private CartDTO curCart;
    private LinkedHashMap<String,Object> cartAnon;
    private List<PaymentTypeDTO> paymentType;
    private List<ShippingDTO> shippingType;
    private UserDTO user;
}
