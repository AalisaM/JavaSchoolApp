package jschool.dto;

import lombok.Data;
import java.util.Objects;

@Data
public class ProductRestDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private String imageSource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRawDTO)) return false;
        ProductRawDTO that = (ProductRawDTO) o;
        return getId() == that.getId() &&
                getPrice() == that.getPrice() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getImageSource(), that.getImageSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getImageSource());
    }

}
