package jschool.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CategoryDTO {
    private int id;

    @Length(min=2, message = "Name is too short")
    private String name;

    @NotNull(message = "Parent category should not be null")
    private Integer parentId;

    private Set<ProductRawDTO> products;
    private boolean isDeleted;

}
