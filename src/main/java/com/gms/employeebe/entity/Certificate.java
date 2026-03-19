package com.gms.employeebe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certificate")
@Getter @Setter @NoArgsConstructor
public class Certificate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code; // PMP, AGILE, IELTS, ...
}