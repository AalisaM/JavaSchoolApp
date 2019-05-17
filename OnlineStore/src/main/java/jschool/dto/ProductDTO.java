package jschool.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
public class ProductDTO {
    private int id;

    @Length(min=2, message = "Name is too short")
    private String name;

    @Size(min = 10, max = 4000000, message = "Description should be from 10 to 40000 symbols")
    private String description;

    @Min(value = 1, message = "Price should not be <= 0")
    @NotNull(message = "Price should not be empty")
    private int price;

    @Min(value = 1, message = "Players amount cannot be <= 0")
    private int minPlayerAmount;

    @Min(value = 1, message = "Players amount cannot be <= 0")
    private int maxPlayerAmount;

    private int weight;
    private int volume;
    private int amount;

    private MultipartFile uploadFile;
    private String imageSource;
    private CategoryRawDTO category;
    private String rule;

    private Set<OrderProductDTO> orderProducts;
    private List<CartItemDTO> cartItem;

    private boolean deleted;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", volume=" + volume +
                ", amount=" + amount +
                ", minPlayerAmount=" + minPlayerAmount +
                ", maxPlayerAmount=" + maxPlayerAmount +
                ", uploadFile=" + uploadFile +
                ", imageSource='" + imageSource + '\'' +
                ", category=" + category +
                ", rule='" + rule + '\'' +
                ", orderProducts=" + orderProducts +
                ", cartItem=" + cartItem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        ProductDTO that = (ProductDTO) o;
        return getId() == that.getId() &&
                getPrice() == that.getPrice() &&
                getWeight() == that.getWeight() &&
                getVolume() == that.getVolume() &&
                getAmount() == that.getAmount() &&
                getMinPlayerAmount() == that.getMinPlayerAmount() &&
                getMaxPlayerAmount() == that.getMaxPlayerAmount() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getImageSource(), that.getImageSource()) &&
                Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getWeight(), getVolume(), getAmount(), getMinPlayerAmount(), getMaxPlayerAmount(), getImageSource(), getCategory());
    }

}
