package jschool.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Data
public class ShippingDTO {
    private int id;

    @Length(min=2, message = "Name is too short")
    private String type;

    private boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShippingDTO)) return false;
        ShippingDTO that = (ShippingDTO) o;
        return getId() == that.getId() &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType());
    }

}
