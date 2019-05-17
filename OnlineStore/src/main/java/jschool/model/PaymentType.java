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
@Table(name="payment_type")
@Data
public class PaymentType {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="type")
	private String type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="paymentType", cascade=CascadeType.ALL)
	private Set<Order> orders;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PaymentType)) return false;
		PaymentType that = (PaymentType) o;
		return getId() == that.getId() &&
				Objects.equals(getType(), that.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getType());
	}


	@Override
	public String toString(){
		return "id="+id+", name="+type;
	}


}
