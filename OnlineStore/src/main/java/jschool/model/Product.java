package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="product")
@Data
public  class Product {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="deleted")
    private Boolean deleted;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private int price;


    @Column(name="weight")
    private int weight;

    @Column(name="volume")
    private int volume;

    @Column(name="amount")
    private int amount;

    @Column(name="minPlayerAmount")
    private int minPlayerAmount;

    @Column(name="maxPlayerAmount")
    private int maxPlayerAmount;

    @Column(name="imageSource")
    private String imageSource;

    @Column(name="rule")
    private String rule;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<CartItem> cartItem;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<OrderProduct> orderProducts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return (id == product.id) &&
                name.equals(product.name) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", volume=" + volume +
                ", amount=" + amount +
                ", minPlayerAmount=" + minPlayerAmount +
                ", maxPlayerAmount=" + maxPlayerAmount +
                ", imageSource='" + imageSource + '\'' +
                ", rule='" + rule + '\'' +
                ", category=" + category.getName() +
                '}';
    }
}
