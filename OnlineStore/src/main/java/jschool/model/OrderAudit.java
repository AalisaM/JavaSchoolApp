package jschool.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="order_audit")
@Data
public class OrderAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User user;

    @Column(name="isPaid")
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "status_prev")
    private OrderStatus prevOrderStatus;

    @ManyToOne
    @JoinColumn(name = "status_now")
    private OrderStatus curOrderStatus;

    @Column(name="date")
    private java.sql.Timestamp date;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
