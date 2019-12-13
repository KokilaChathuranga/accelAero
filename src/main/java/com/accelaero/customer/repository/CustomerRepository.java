package com.accelaero.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.accelaero.customer.model.Customer;
import com.accelaero.customer.model.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Transactional
    Customer findByUser(User user);
}
