package jschool.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="order_history_product")
@Data
public class OrderProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_history_id")
    private OrderHistory order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_name")
    private String productName;

    @Column(name="price")
    private int price;

    @Column(name="amount")
    private int amount;

    @Override
    public String toString() {
        return "OrderProductHistory{" +
                "id=" + id +
                ", order=" + order.getOrderId() +
                ", product=" + product.getName() +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
