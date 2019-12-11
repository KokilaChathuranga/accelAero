package com.hellokoding.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellokoding.auth.model.Customer;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer update(User user, Customer customer, Customer existingCustomer) {

        if (existingCustomer != null) {
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setMobileNo(customer.getMobileNo());
        } else {
            existingCustomer = customer;
        }
        existingCustomer.setUser(user);
        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer findByCustomerId(Long customerId) {
        return customerRepository.getOne(customerId);
    }

    @Override
    public Customer findByUser(User user) {
        return customerRepository.findByUser(user);
    }
}
