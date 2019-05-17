package jschool.dto;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class OrderHistoryDTO {

    private int id;
    private String email;
    private String fullName;
    private String address;
    private PaymentTypeDTO paymentType;
    private PaymentStatusDTO paymentStatus;
    private String shippingType;
    private OrderStatusDTO orderStatus;
    private int totalPrice;
    private int amount;
    private List<OrderProductDTO> orderProducts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHistoryDTO)) return false;
        OrderHistoryDTO orderDTO = (OrderHistoryDTO) o;
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
    public String toString() {
        return "OrderHistoryDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", paymentType=" + paymentType +
                ", paymentStatus=" + paymentStatus +
                ", shippingType=" + shippingType +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                ", amount=" + amount +
                ", orderProducts=" + orderProducts +
                '}';
    }

}
