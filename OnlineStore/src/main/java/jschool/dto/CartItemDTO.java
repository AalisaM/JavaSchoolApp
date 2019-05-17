package jschool.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private int id;
    private ProductRawDTO product;
    private int amount;
    private int price;

}
