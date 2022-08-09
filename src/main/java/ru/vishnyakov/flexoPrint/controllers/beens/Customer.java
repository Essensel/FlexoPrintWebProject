package ru.vishnyakov.flexoPrint.controllers.beens;



import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "customers")
public class Customer {
    @Id
    @GenericGenerator(name = "native_generator", strategy = "native")
    @GeneratedValue(generator = "native_generator")
    private Long id;
    private String name;
    private String last_name;
    private String email;
    private String name_of_company;
    private String address;
    private String phone;
   private String comment;

public Customer(){

}
    public Customer(String name, String last_name,String nameOfCompany, String email, String address, String phone,String comment) {
      this.name = name;
      this.last_name = last_name;
      this.name_of_company = nameOfCompany;
      this.email = email;
      this.address = address;
      this.phone = phone;
     this.comment = comment;
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
}
