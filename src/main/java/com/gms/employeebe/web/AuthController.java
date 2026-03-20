package com.gms.employeebe.web;

import com.gms.employeebe.security.CustomUserDetails;
import com.gms.employeebe.security.JwtUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        Long userId = ((CustomUserDetails) auth.getPrincipal()).getUser().getId();
        String token = jwtUtils.generateToken(userId); // subject = userId
        return ResponseEntity.ok(Map.of("tokenType", "Bearer", "accessToken", token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

        return ResponseEntity.ok(Map.of(
                "id", principal.getUser().getId(),
                "username", principal.getUsername(),
                "role", principal.getUser().getRole().name()
        ));
    }

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}