package ru.vishnyakov.flexoPrint.controllers.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vishnyakov.flexoPrint.controllers.beens.Order;
import ru.vishnyakov.flexoPrint.controllers.repositories.OrdersRepository;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;
public Order getOrderById(Long id){
  Optional<Order> orders =   ordersRepository.findById(id);
  Order curentOrder;
  if(orders.isPresent()){
      curentOrder = orders.get();
  } else curentOrder = null;
  return curentOrder;
}

    public void addOrder (Order order){
ordersRepository.save(order);
    }
}
