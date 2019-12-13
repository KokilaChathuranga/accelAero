package com.accelaero.customer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accelaero.customer.model.Customer;
import com.accelaero.customer.model.Order;
import com.accelaero.customer.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order create(Customer customer, long restaurantId, long foodId, long quantity, Date orderDate, long unitPrice, long totalPrice) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurantId(restaurantId);
        order.setFoodId(foodId);
        order.setOrderDate(orderDate);
        order.setUnitPrice(unitPrice);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderByCustomerAndId(Customer customer, long id) {
        return orderRepository.findByCustomerAndOrderId(customer, id);
    }
}
