package jschool.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;

@Data
public class ProductRawDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private int weight;
    private int volume;
    private int amount;
    private int minPlayerAmount;
    private int maxPlayerAmount;
    private MultipartFile uploadFile;
    private String imageSource;
    private CategoryDTO category;
    private String imageFile;
    private boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRawDTO)) return false;
        ProductRawDTO that = (ProductRawDTO) o;
        return getId() == that.getId() &&
                getPrice() == that.getPrice() &&
                getWeight() == that.getWeight() &&
                getVolume() == that.getVolume() &&
                getAmount() == that.getAmount() &&
                getMinPlayerAmount() == that.getMinPlayerAmount() &&
                getMaxPlayerAmount() == that.getMaxPlayerAmount() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getUploadFile(), that.getUploadFile()) &&
                Objects.equals(getImageSource(), that.getImageSource()) &&
                Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getWeight(), getVolume(), getAmount(), getMinPlayerAmount(), getMaxPlayerAmount(), getUploadFile(), getImageSource(), getCategory());
    }

}
