package com.accelaero.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accelaero.customer.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
