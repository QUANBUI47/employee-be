package com.gms.employeebe.service;

import com.gms.employeebe.entity.User;

import java.util.Optional;

public interface UserService {
    User create(String username, String rawPassword, User.Role role);
    Optional<User> findByUsername(String username);
    User getById(Long id);
}