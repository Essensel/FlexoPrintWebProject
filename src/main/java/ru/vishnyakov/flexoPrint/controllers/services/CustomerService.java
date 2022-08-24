package ru.vishnyakov.flexoPrint.controllers.services;

import ru.vishnyakov.flexoPrint.controllers.beens.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ru.vishnyakov.flexoPrint.controllers.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAll() {
        List<Customer> customersList = (List<Customer>) customerRepository.findAll();
        return customersList;
    }
    public Customer getCustomerById(Long id){
        Customer curentCustomer = null;
        Optional<Customer> customer = customerRepository.findById(id);
       if(customer.isPresent()){
           curentCustomer = customer.get();
       }
        return curentCustomer;
    }

    public long getCount() {
        long count = customerRepository.count();
        if (count == 0) {
            count = 999;
        }
        return count;

    }


    public Long saveCustomer(Customer newCustomer) {
        Long id = customerRepository.save(newCustomer).getId();
        return id;
    }
    public Long checkNewCustomer(Customer newCustomer){
     Long newCustomerId = null;
        ArrayList<Customer> list = (ArrayList<Customer>) this.getAll();
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(newCustomer)){
               newCustomerId = list.get(i).getId();
            }
        }
        return newCustomerId;
    }
}

