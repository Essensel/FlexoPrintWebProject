package ru.vishnyakov.flexoPrint.controllers.beens;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "customers")
public class Customer {
    @Id
    @GenericGenerator(name = "native_generator", strategy = "native")
    @GeneratedValue(generator = "native_generator")
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String last_name;
    private String email;
    private String name_of_company;
    private String address;
    private String phone;
    private String comment;

    @OneToMany (mappedBy = "orderСreator", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Collection<Order> orders;

    public Customer() {

    }

    public Customer(String name, String last_name, String nameOfCompany, String email, String address, String phone, String comment) {
        this.name = name;
        this.last_name = last_name;
        this.name_of_company = nameOfCompany;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getName_of_company() {
        return name_of_company;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setName_of_company(String nameOfCompany) {
        this.name_of_company = nameOfCompany;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name) &&
                last_name.equals(customer.last_name) &&
                name_of_company.equals(customer.name_of_company) &&
                phone.equals(customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, last_name, name_of_company, phone);
    }
}
