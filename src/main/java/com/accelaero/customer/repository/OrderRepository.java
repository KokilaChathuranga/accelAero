package com.accelaero.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accelaero.customer.model.Customer;
import com.accelaero.customer.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByCustomerAndOrderId(Customer customer, long id);
}
