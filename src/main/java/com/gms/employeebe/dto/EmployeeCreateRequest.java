package com.gms.employeebe.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EmployeeCreateRequest {
    @NotBlank @Size(max = 150) private String name;
    @NotNull @Past private LocalDate dob;
    @Size(max = 255) private String address;
    @NotBlank @Pattern(regexp = "^\\+?\\d{9,15}$") private String phone;

    private Set<Long> languageIds;
    private Set<Long> certificateIds;
}