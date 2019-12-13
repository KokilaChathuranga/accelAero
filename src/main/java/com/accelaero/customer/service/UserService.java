package com.accelaero.customer.service;

import com.accelaero.customer.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
