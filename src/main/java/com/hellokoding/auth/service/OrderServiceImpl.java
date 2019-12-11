package com.hellokoding.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.Order;
import com.hellokoding.auth.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order create(Customer customer, long restaurantId, long foodId, long quantity, Date orderDate) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurantId(restaurantId);
        order.setFoodId(foodId);
        order.setOrderDate(orderDate);
        return orderRepository.save(order);
    }
}
