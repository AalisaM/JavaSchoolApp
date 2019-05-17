package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name="orders")
public  class Order {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "payment_status_id")
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "shipping_type_id")
    private ShippingType shippingType;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Column(name="order_date")
    private java.sql.Timestamp date;

    @Column(name="totalPrice")
    private int totalPrice;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST},fetch=FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                getTotalPrice() == order.getTotalPrice() &&
                Objects.equals(getUser(), order.getUser()) &&
                Objects.equals(getPaymentType(), order.getPaymentType()) &&
                Objects.equals(getPaymentStatus(), order.getPaymentStatus()) &&
                Objects.equals(getShippingType(), order.getShippingType()) &&
                Objects.equals(getOrderStatus(), order.getOrderStatus()) &&
                Objects.equals(getDate(), order.getDate()) &&
                Objects.equals(getOrderProducts(), order.getOrderProducts());
    }

    @Override
    public String toString(){
        return "Order: " + this.getId();
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPaymentType(), getPaymentStatus(), getShippingType(), getOrderStatus(), getDate(), getTotalPrice(), getOrderProducts());
    }
    public boolean isActive() {
        return !this.orderStatus.getStatus().equals("cancelled");
    };

    public boolean isPaid() {
        return this.paymentStatus.getStatus().equals("paid");
    }
}
