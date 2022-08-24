package ru.vishnyakov.flexoPrint.controllers.calculations;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vishnyakov.flexoPrint.controllers.beens.Order;
import ru.vishnyakov.flexoPrint.controllers.services.OrderService;

public class CalculationService {
    @Autowired
  private OrderService orderService;
    @Autowired
private Calculation calculation;

    public double getPriceCirculation(Long circulatioId){
      orderService.getOrderById(circulatioId);
    //  calculation.getPrimeCostOfCirculation()
        return 0.0;
    }
}
