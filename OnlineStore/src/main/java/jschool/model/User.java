package jschool.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name="user")
@Data
public  class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="fullName")
    private String fullName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="birth")
    private Date birth;

    @Column(name="password")
    private String password;

    @Column(name="isAdmin")
    private boolean admin;


    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address activeAddressId;

    @ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.REMOVE,CascadeType.ALL })
    @JoinTable(
            name = "address_user",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "address_id") }
    )
    private Set<Address> addresses;

    @ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.REMOVE,CascadeType.ALL })
    @JoinTable(
            name = "role_user",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
    private Set<Order> orders;


    @Column(name="enabled")
    private Boolean enabled;

    @OneToOne(mappedBy = "user")
    private Cart cart;


    @Override
    public String toString(){
        return "Name : " + this.getFullName() + "Addresses : " + Arrays.toString(this.getAddresses().toArray());
    }

    @Override
    public int hashCode(){
        if (Objects.isNull(this.email)){
            this.email = "";
        }
        return (this.id +
                this.email.hashCode());
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof User){
            User u2 = (User) o;
            return u2 == this || (u2.id == this.id) ||
                    (u2.email.equals(this.email));
        }
        return false;
    }

}
