package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="order_history")
@Data
public class OrderHistory {
    @Id
    @Column(name="order_id",unique = true, nullable = false)
    private int orderId;

    /** user with this associated settings */
    @MapsId
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "payment_type")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "shippingType_name")
    private String shipping;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<OrderProductHistory> orderProductHistories;

    @Column(name="address")
    private String address;

    @Column(name="client_name")
    private String clientName;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name="email")
    private String email;


    @Column(name="date")
    private Timestamp date;


    @Column(name="totalPrice")
    private int totalPrice;

    @Column(name="totalAmount")
    private int totalAmount;

    @Override
    public String toString() {
        return "OrderHistory{" +
                "orderId=" + orderId +
                ", orderStatus=" + orderStatus.getStatus() +
                ", shipping='" + shipping + '\'' +
                ", address='" + address + '\'' +
                ", client_name='" + clientName + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
