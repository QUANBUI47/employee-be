package com.gms.employeebe.controller;

import com.gms.employeebe.dto.EmployeeCreateRequest;
import com.gms.employeebe.dto.EmployeeResponse;
import com.gms.employeebe.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // USER + ADMIN: xem danh sách
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/employees")
    public Page<EmployeeResponse> list(@RequestParam(required = false) String q,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return employeeService.search(q, pageable);
    }

    // USER + ADMIN: xem chi tiết
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/employees/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return employeeService.get(id);
    }

    // ADMIN: tạo
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employees")
    public EmployeeResponse create(@Valid @RequestBody EmployeeCreateRequest req) {
        return employeeService.create(req);
    }

    // ADMIN: cập nhật
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/employees/{id}")
    public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeCreateRequest req) {
        return employeeService.update(id, req);
    }

    // ADMIN: xoá
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}