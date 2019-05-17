package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="cartitem")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name="totalPrice")
    private int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return getId() == cartItem.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount(), getPrice());
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", product=" + product.getId() +
                ", amount=" + amount +
                ", cart=" + cart.getUserId() +
                ", price=" + price +
                '}';
    }
}
