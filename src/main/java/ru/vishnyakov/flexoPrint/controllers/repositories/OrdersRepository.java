package ru.vishnyakov.flexoPrint.controllers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.vishnyakov.flexoPrint.controllers.beens.Order;

public interface OrdersRepository extends JpaRepository<Order, Long> {
}
