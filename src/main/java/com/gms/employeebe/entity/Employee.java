package com.gms.employeebe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter @Setter @NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    private LocalDate dob;

    @Column(length = 255)
    private String address;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "employee_language",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "employee_certificate",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    private Set<Certificate> certificates = new HashSet<>();
}