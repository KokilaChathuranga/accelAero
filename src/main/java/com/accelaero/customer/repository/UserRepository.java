package com.accelaero.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accelaero.customer.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
