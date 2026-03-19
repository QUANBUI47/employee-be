package com.gms.employeebe.controller;

import com.gms.employeebe.entity.User;
import com.gms.employeebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@RequestParam String username,
                       @RequestParam String password,
                       @RequestParam(defaultValue = "USER") User.Role role) {
        return userService.create(username, password, role);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getById(id);
    }
}