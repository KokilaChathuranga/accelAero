package com.hellokoding.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Transactional
    Customer findByUser(User user);
}
