package com.gms.employeebe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "language")
@Getter @Setter @NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String level;
}