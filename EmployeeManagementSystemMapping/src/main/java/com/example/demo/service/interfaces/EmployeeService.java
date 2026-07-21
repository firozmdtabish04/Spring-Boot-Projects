package com.example.demo.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.EmployeeRequest;
import com.example.demo.dto.response.EmployeeResponse;

public interface EmployeeService {

	EmployeeResponse createEmployee(EmployeeRequest request);

	EmployeeResponse getEmployeeById(Long id);

	Page<EmployeeResponse> getAllEmployees(Pageable pageable);

	Page<EmployeeResponse> searchEmployees(String keyword, Pageable pageable);

	Page<EmployeeResponse> getEmployeesByDepartment(Long departmentId, Pageable pageable);

	EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

	void deleteEmployee(Long id);
}