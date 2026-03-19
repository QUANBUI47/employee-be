package com.gms.employeebe.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeResponse {
    private Long id;
    private String name;
    private LocalDate dob;
    private String address;
    private String phone;
    private List<String> languages;    // "ENGLISH-A", "JAPANESE-N3"
    private List<String> certificates; // "PMP", "AGILE", "IELTS"
}