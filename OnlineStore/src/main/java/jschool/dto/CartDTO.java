package jschool.dto;

import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class CartDTO {
    private int user_id;
    private UserDTO user;

    private List<CartItemDTO> cartItem;
    private int totalPrice;
    private int totalAmount;


    public List<CartItemDTO> getCartItem() {
        cartItem.sort(Comparator.comparing(CartItemDTO::getId));
        return cartItem;
    }

}
