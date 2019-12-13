package com.accelaero.customer.service;

import java.util.Date;

import com.accelaero.customer.model.Customer;
import com.accelaero.customer.model.Order;

public interface OrderService {
    Order create(Customer customer, long restaurantId, long foodId, long quantity, Date orderDate, long unitPrice, long totalPrice);

    Order getOrderByCustomerAndId(Customer customer, long id);
}