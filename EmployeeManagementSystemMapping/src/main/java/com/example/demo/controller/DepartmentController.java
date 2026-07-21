package com.example.demo.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.DepartmentRequest;
import com.example.demo.dto.response.DepartmentResponse;
import com.example.demo.service.interfaces.DepartmentService;
import com.example.demo.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
@Validated
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
			@Valid @RequestBody DepartmentRequest request) {

		DepartmentResponse response = departmentService.createDepartment(request);

		ApiResponse<DepartmentResponse> apiResponse = ApiResponse.<DepartmentResponse>builder().success(true)
				.message("Department created successfully").data(response).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable Long id) {

		DepartmentResponse response = departmentService.getDepartmentById(id);

		ApiResponse<DepartmentResponse> apiResponse = ApiResponse.<DepartmentResponse>builder().success(true)
				.message("Department fetched successfully").data(response).build();

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {

		List<DepartmentResponse> departments = departmentService.getAllDepartments();

		ApiResponse<List<DepartmentResponse>> apiResponse = ApiResponse.<List<DepartmentResponse>>builder()
				.success(true).message("Department list fetched successfully").data(departments).build();

		return ResponseEntity.ok(apiResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(@PathVariable Long id,
			@Valid @RequestBody DepartmentRequest request) {

		DepartmentResponse response = departmentService.updateDepartment(id, request);

		ApiResponse<DepartmentResponse> apiResponse = ApiResponse.<DepartmentResponse>builder().success(true)
				.message("Department updated successfully").data(response).build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Long id) {

		departmentService.deleteDepartment(id);

		ApiResponse<String> apiResponse = ApiResponse.<String>builder().success(true)
				.message("Department deleted successfully").data("Department deleted successfully.").build();

		return ResponseEntity.ok(apiResponse);
	}
}