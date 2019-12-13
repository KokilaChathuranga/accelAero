package com.accelaero.customer.service;

import com.accelaero.customer.model.User;

public interface UserService {
    User save(User user);

    User findByUsername(String username);
}
