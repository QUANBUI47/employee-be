package com.gms.employeebe.repository;

import com.gms.employeebe.entity.Employee;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone, Pageable pageable);
}