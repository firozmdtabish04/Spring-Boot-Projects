package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.DesignationRequest;
import com.example.demo.dto.response.DesignationResponse;
import com.example.demo.service.interfaces.DesignationService;
import com.example.demo.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/designations")
@RequiredArgsConstructor
public class DesignationController {

	private final DesignationService designationService;

	@PostMapping
	public ResponseEntity<ApiResponse<DesignationResponse>> createDesignation(
			@Valid @RequestBody DesignationRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.<DesignationResponse>builder().success(true)
						.message("Designation created successfully").data(designationService.createDesignation(request))
						.build());

	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<DesignationResponse>>> getAll() {

		return ResponseEntity.ok(ApiResponse.<List<DesignationResponse>>builder().success(true)
				.message("Designation list fetched successfully").data(designationService.getAllDesignations())
				.build());

	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DesignationResponse>> getById(@PathVariable Long id) {

		return ResponseEntity.ok(ApiResponse.<DesignationResponse>builder().success(true)
				.message("Designation fetched successfully").data(designationService.getDesignationById(id)).build());

	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DesignationResponse>> update(@PathVariable Long id,
			@Valid @RequestBody DesignationRequest request) {

		return ResponseEntity
				.ok(ApiResponse.<DesignationResponse>builder().success(true).message("Designation updated successfully")
						.data(designationService.updateDesignation(id, request)).build());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {

		designationService.deleteDesignation(id);

		return ResponseEntity.ok(ApiResponse.<String>builder().success(true).message("Designation deleted successfully")
				.data("Deleted").build());

	}

}