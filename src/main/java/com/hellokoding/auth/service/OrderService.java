package com.hellokoding.auth.service;

import java.util.Date;

import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.Order;

public interface OrderService {
    Order create(Customer customer, long restaurantId, long foodId, long quantity, Date orderDate, long unitPrice, long totalPrice);

    Order getOrderByCustomerAndId(Customer customer, long id);
}