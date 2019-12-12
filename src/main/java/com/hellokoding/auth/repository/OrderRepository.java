package com.hellokoding.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByCustomerAndOrderId(Customer customer, long id);
}
