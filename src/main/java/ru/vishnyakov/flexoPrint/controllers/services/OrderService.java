package ru.vishnyakov.flexoPrint.controllers.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vishnyakov.flexoPrint.controllers.beens.Order;
import ru.vishnyakov.flexoPrint.controllers.repositories.OrdersRepository;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class OrderService {
    private static ArrayList<Order> allOrders = new ArrayList<>();

    @Autowired
    private OrdersRepository ordersRepository;

    public Order getOrderById(Long id) {
        Optional<Order> orders = ordersRepository.findById(id);
        Order curentOrder;
        if (orders.isPresent()) {
            curentOrder = orders.get();
        } else curentOrder = null;
        return curentOrder;
    }

    public List<Order> getOrdersList(int page, int limit) {
        Page<Order> resultPage = ordersRepository.findAll(PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "orderDate")));

        return resultPage.getContent();
    }

    public List<Order> getPendingOrdersList(int page, int limit) {
        Page<Order> resultPage = ordersRepository.findAll(PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "orderDate")));
        ArrayList<Order> ordersList = (ArrayList<Order>) resultPage.getContent();
        if (ordersList.size() > 0) {
            Iterator<Order> orderIterator = ordersList.iterator();
            while (orderIterator.hasNext()) {//до тех пор, пока в списке есть элементы
                Order nextOrder = orderIterator.next();//получаем следующий элемент
                if (nextOrder.getStatus().equals("НЕОБРАБОТАННО")) {
                    orderIterator.remove();//удаляем заказ со статусом необработано с нужным именем
                }
            }
        }
        return ordersList;
    }

    public boolean deleteById(Long id) {
        if (id != null) {

            ordersRepository.deleteById(id);

            return true;
        }
        return false;
    }

    public void addOrder(Order order) {
        ordersRepository.save(order);
    }

    public int getCount() {
        return
                (int) this.ordersRepository.count();
    }
}
