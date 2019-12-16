package com.accelaero.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accelaero.customer.model.Customer;
import com.accelaero.customer.model.User;
import com.accelaero.customer.repository.CustomerRepository;

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
