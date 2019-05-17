package jschool.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class OrderStatusDTO {
    private int id;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatusDTO)) return false;
        OrderStatusDTO that = (OrderStatusDTO) o;
        return getId() == that.getId() &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus());
    }

    public boolean isPaid(){
        return (this.status.equals("paid") || this.status.equals("delivered"));
    }

}
