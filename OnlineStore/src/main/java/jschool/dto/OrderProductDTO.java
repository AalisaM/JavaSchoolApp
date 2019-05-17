package jschool.dto;

import lombok.Data;
import java.util.Objects;

@Data
public class OrderProductDTO {

    private int productid;

    private int amount;

    private int price;

    private String productName;

    public String toString(){
        return "oPdto : " + amount + "; " + price + "; " + productid + ";" + productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductDTO)) return false;
        OrderProductDTO that = (OrderProductDTO) o;
        return getProductid() == that.getProductid() &&
                getAmount() == that.getAmount() &&
                getPrice() == that.getPrice() &&
                Objects.equals(getProductName(), that.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductid(), getAmount(), getPrice(), getProductName());
    }
}
