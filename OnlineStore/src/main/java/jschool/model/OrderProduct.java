package jschool.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="order_product")
@Data
public class OrderProduct {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Column(name="totalPrice")
    private int price;


    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", order=" + order.getId() +
                ", product=" + product.getName() +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
