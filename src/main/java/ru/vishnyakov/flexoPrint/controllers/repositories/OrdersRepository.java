package ru.vishnyakov.flexoPrint.controllers.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vishnyakov.flexoPrint.controllers.beens.Order;

public interface OrdersRepository extends CrudRepository<Order, Long> {
}
