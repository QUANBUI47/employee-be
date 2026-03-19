package com.gms.employeebe.service;

import com.gms.employeebe.dto.EmployeeCreateRequest;
import com.gms.employeebe.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<EmployeeResponse> search(String q, Pageable pageable);

    EmployeeResponse get(Long id);

    EmployeeResponse create(EmployeeCreateRequest req);

    EmployeeResponse update(Long id, EmployeeCreateRequest req);

    void delete(Long id);
}