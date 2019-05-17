package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="address")
@Data
public class Address {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy="activeAddressId", cascade=CascadeType.ALL)
    private Set<User> activeUsers;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "addresses", cascade=CascadeType.ALL)
    Set<User> users;

    @Override
    public String toString(){
        return this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address1 = (Address) o;
        return getId() == address1.getId() &&
                Objects.equals(getAddress(), address1.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress());
    }

}
