package ru.vishnyakov.flexoPrint.controllers.repositories;


import ru.vishnyakov.flexoPrint.controllers.beens.Customer;
import org.springframework.data.repository.CrudRepository;



public interface CustomerRepository extends CrudRepository<Customer,Long> {
}

