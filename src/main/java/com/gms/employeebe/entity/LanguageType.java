package com.gms.employeebe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "language_type")
@Getter @Setter @NoArgsConstructor
public class LanguageType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code; // ENGLISH, JAPANESE
}