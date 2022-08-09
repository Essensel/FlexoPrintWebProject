package ru.vishnyakov.flexoPrint.controllers.services;

import ru.vishnyakov.flexoPrint.controllers.beens.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ru.vishnyakov.flexoPrint.controllers.repositories.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAll(){
       List<Customer> resultPage = (List<Customer>)customerRepository.findAll();
       return resultPage;
       }
       public long getCount() {
           long count = customerRepository.count();
           if (count == 0) {
               count = 999;
           } return count;

       }



public Long saveCustomer(Customer newCustomer){
        Long id = customerRepository.save(newCustomer).getId();
return id;
}}

