package com.gms.employeebe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "language",
        uniqueConstraints = @UniqueConstraint(columnNames = {"language_type_id","level"})
)
@Getter @Setter @NoArgsConstructor
public class Language {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_type_id", nullable = false)
    private LanguageType languageType;

    @Column(nullable = false, length = 10)
    private String level; // A/B/C or N1..N5
}