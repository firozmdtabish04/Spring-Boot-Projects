package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.EmployeeRequest;
import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.service.interfaces.EmployeeService;
import com.example.demo.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(@Valid @RequestBody EmployeeRequest request) {

		EmployeeResponse response = employeeService.createEmployee(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<EmployeeResponse>builder().success(true)
				.message("Employee created successfully").data(response).build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id) {

		return ResponseEntity.ok(ApiResponse.<EmployeeResponse>builder().success(true)
				.message("Employee fetched successfully").data(employeeService.getEmployeeById(id)).build());
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> getAllEmployees(Pageable pageable) {

		return ResponseEntity.ok(ApiResponse.<Page<EmployeeResponse>>builder().success(true)
				.message("Employee list fetched successfully").data(employeeService.getAllEmployees(pageable)).build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(@PathVariable Long id,
			@Valid @RequestBody EmployeeRequest request) {

		return ResponseEntity.ok(ApiResponse.<EmployeeResponse>builder().success(true)
				.message("Employee updated successfully").data(employeeService.updateEmployee(id, request)).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id) {

		employeeService.deleteEmployee(id);

		return ResponseEntity.ok(ApiResponse.<String>builder().success(true).message("Employee deleted successfully")
				.data("Deleted").build());
	}

	@GetMapping("/salary")

	public ResponseEntity<ApiResponse<List<EmployeeResponse>>> salaryGreaterThan(@RequestParam Double salary) {

		return ResponseEntity.ok(

				ApiResponse.<List<EmployeeResponse>>builder().success(true).message("Employees fetched")
						.data(employeeService.getEmployeesWithSalary(salary)).build());
	}

}