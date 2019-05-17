package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author pankaj
 *
 */
@Entity
@Table(name="category")
@Data
public class Category {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="name")
	private String name;


	@Column(name="Parent_Category_id")
	private Integer parentId;

	@Column(name="isDeleted")
	private boolean isDeleted;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="category", cascade=CascadeType.ALL)
	private Set<Product> products;


	public boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Category)) return false;
		Category category = (Category) o;
		return getId() == category.getId() &&
				Objects.equals(getName(), category.getName()) &&
				Objects.equals(getParentId(), category.getParentId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getParentId());
	}

	@Override
	public String toString(){
		return "id="+id+", name="+name;
	}

}
