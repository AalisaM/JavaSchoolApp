package jschool.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="cart")
@Data
public  class Cart {

    @Id
    @Column(name="user_id",unique = true, nullable = false)
    private int userId;

    /** user with this associated settings */
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<CartItem> cartItem;

    @Column(name = "totalPrice")
    private int totalPrice;

    @Column(name = "totalAmount")
    private int totalAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return getUserId() == cart.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTotalPrice(), getTotalAmount());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user_id=" + userId +
                ", user=" + user +
                ", cartItem=" + cartItem.size() +
                ", totalPrice=" + totalPrice +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
