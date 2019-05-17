package jschool.dto;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class OrderDTO {

    private int id;
    private String email;
    private String address;
    private PaymentTypeDTO paymentType;
    private PaymentStatusDTO paymentStatus;
    private ShippingDTO shippingType;
    private OrderStatusDTO orderStatus;
    private int totalPrice;
    private int amount;
    private List<OrderProductDTO> orderProducts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return getId() == orderDTO.getId() &&
                getTotalPrice() == orderDTO.getTotalPrice() &&
                getAmount() == orderDTO.getAmount() &&
                Objects.equals(getEmail(), orderDTO.getEmail()) &&
                Objects.equals(getAddress(), orderDTO.getAddress()) &&
                Objects.equals(paymentType, orderDTO.paymentType) &&
                Objects.equals(paymentStatus, orderDTO.paymentStatus) &&
                Objects.equals(shippingType, orderDTO.shippingType) &&
                Objects.equals(orderStatus, orderDTO.orderStatus) &&
                Objects.equals(getOrderProducts(), orderDTO.getOrderProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getAddress(), paymentType, paymentStatus, shippingType, orderStatus, getTotalPrice(), getAmount(), getOrderProducts());
    }

    @Override
    public String toString(){
        return (Objects.isNull(email) ? "" : email) + ": " +
                (Objects.isNull(address) ? "" : address) + ";\n PaymentType: " +
                (Objects.isNull(paymentType) ? "" : paymentType.getType()) + "; status: " +
                (Objects.isNull(paymentStatus) ? "" : paymentStatus) + ";" +
                "shipping : " +  (Objects.isNull(shippingType) ? "" : shippingType) + "; order status : " +
                (Objects.isNull(orderStatus) ? "" : orderStatus) + " ; total price : " +
                totalPrice  + "\n Products :" +
                (Objects.isNull(orderProducts) ? "" : orderProducts.toString());
    }

}
