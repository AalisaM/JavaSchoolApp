package jschool.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class CategoryRawDTO {
    private int id;
    private String title;
    private Integer parentId;
    private int productAmount;
    private boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryRawDTO)) return false;
        CategoryRawDTO that = (CategoryRawDTO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getParentId(), that.getParentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getParentId());
    }

}
