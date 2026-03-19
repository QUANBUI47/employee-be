package com.gms.employeebe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "`user`")
@Getter @Setter @NoArgsConstructor
public class User {

    public enum Role { ADMIN, USER }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role = Role.USER;

    // cột có DEFAULT CURRENT_TIMESTAMP bên DB → không set từ app
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}