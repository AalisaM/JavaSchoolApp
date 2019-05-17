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
@Table(name="order_status")
@Data
public class OrderStatus {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="status")
	private String status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="orderStatus", cascade=CascadeType.ALL)
	private Set<Order> orders;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="curOrderStatus", cascade=CascadeType.ALL)
	private Set<OrderAudit> curs;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="prevOrderStatus", cascade=CascadeType.ALL)
	private Set<OrderAudit> prevs;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OrderStatus)) return false;
		OrderStatus that = (OrderStatus) o;
		return getId() == that.getId() &&
				Objects.equals(getStatus(), that.getStatus());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getStatus());
	}

	@Override
	public String toString(){
		return "id="+id+", name="+status;
	}

}
