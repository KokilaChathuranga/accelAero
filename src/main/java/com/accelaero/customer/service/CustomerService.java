package com.accelaero.customer.service;

import com.accelaero.customer.model.Customer;
import com.accelaero.customer.model.User;

public interface CustomerService {
    Customer update(User user, Customer customer, Customer existingCustomer);

    Customer findByCustomerId(Long customerId);

    Customer findByUser(User user);
}
