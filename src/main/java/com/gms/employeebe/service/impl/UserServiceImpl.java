package com.gms.employeebe.service.impl;

import com.gms.employeebe.entity.User;
import com.gms.employeebe.repository.UserRepository;
import com.gms.employeebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User create(String username, String rawPassword, User.Role role) {
        if (repo.existsByUsername(username)) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword)); // mã hoá mật khẩu
        u.setRole(role == null ? User.Role.USER : role);
        return repo.save(u);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}