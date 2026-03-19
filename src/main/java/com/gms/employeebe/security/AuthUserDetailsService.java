package com.gms.employeebe.security;

import com.gms.employeebe.entity.User;
import com.gms.employeebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserService userService; // dùng domain service đã có

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(u);
    }

    /** Dùng nếu JWT subject = userId (Long). */
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User u = userService.getById(id); // domain service đã có sẵn getById(Long)
        return new CustomUserDetails(u);
    }
}