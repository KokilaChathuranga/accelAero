package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.User;

public interface CustomerService {
    Customer update(User user, Customer customer, Customer existingCustomer);

    Customer findByCustomerId(Long customerId);

    Customer findByUser(User user);
}
