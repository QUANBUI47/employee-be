package com.gms.employeebe.service.impl;

import com.gms.employeebe.dto.EmployeeCreateRequest;
import com.gms.employeebe.dto.EmployeeResponse;
import com.gms.employeebe.entity.Certificate;
import com.gms.employeebe.entity.Employee;
import com.gms.employeebe.repository.CertificateRepository;
import com.gms.employeebe.repository.EmployeeRepository;
import com.gms.employeebe.repository.LanguageRepository;
import com.gms.employeebe.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final LanguageRepository languageRepo;
    private final CertificateRepository certificateRepo;

    @Override
    public Page<EmployeeResponse> search(String q, Pageable pageable) {
        Page<Employee> page = (q == null || q.isBlank())
                ? employeeRepo.findAll(pageable)
                : employeeRepo.findByNameContainingIgnoreCaseOrPhoneContaining(q, q, pageable);
        return page.map(this::toResponse);
    }

    @Override
    public EmployeeResponse get(Long id) {
        return toResponse(findEntity(id));
    }

    @Override
    public EmployeeResponse create(EmployeeCreateRequest req) {
        Employee e = new Employee();
        apply(e, req);
        try {
            return toResponse(employeeRepo.save(e));
        } catch (DataIntegrityViolationException ex) {
            // phone unique / trigger DB vi phạm ràng buộc
            throw ex;
        }
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeCreateRequest req) {
        Employee e = findEntity(id);
        apply(e, req);
        try {
            return toResponse(employeeRepo.save(e));
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepo.existsById(id)) throw new NoSuchElementException("Employee not found");
        employeeRepo.deleteById(id);
    }

    // ===== helpers =====

    private Employee findEntity(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Employee not found"));
    }

    private void apply(Employee e, EmployeeCreateRequest req) {
        e.setName(req.getName());
        e.setDob(req.getDob());
        e.setAddress(req.getAddress());
        e.setPhone(req.getPhone());

        e.getLanguages().clear();
        if (req.getLanguageIds() != null && !req.getLanguageIds().isEmpty()) {
            e.getLanguages().addAll(languageRepo.findAllById(req.getLanguageIds()));
        }

        e.getCertificates().clear();
        if (req.getCertificateIds() != null && !req.getCertificateIds().isEmpty()) {
            e.getCertificates().addAll(certificateRepo.findAllById(req.getCertificateIds()));
        }
    }

    private EmployeeResponse toResponse(Employee e) {
        EmployeeResponse res = new EmployeeResponse();
        res.setId(e.getId());
        res.setName(e.getName());
        res.setDob(e.getDob());
        res.setAddress(e.getAddress());
        res.setPhone(e.getPhone());

        res.setLanguages(
                e.getLanguages().stream()
                        .map(l -> l.getName() + "-" + l.getLevel())
                        .collect(Collectors.toList())
        );

        res.setCertificates(
                e.getCertificates().stream()
                        .map(Certificate::getName)
                        .collect(Collectors.toList())
        );

        return res;
    }
}