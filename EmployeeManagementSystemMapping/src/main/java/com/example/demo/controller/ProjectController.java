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

import com.example.demo.dto.request.ProjectRequest;
import com.example.demo.dto.response.ProjectResponse;
import com.example.demo.service.interfaces.ProjectService;
import com.example.demo.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody ProjectRequest request) {

		ProjectResponse response = projectService.createProject(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ProjectResponse>builder().success(true)
				.message("Project created successfully").data(response).build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Long id) {

		return ResponseEntity.ok(ApiResponse.<ProjectResponse>builder().success(true)
				.message("Project fetched successfully").data(projectService.getProject(id)).build());
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {

		return ResponseEntity.ok(ApiResponse.<List<ProjectResponse>>builder().success(true)
				.message("Projects fetched successfully").data(projectService.getAllProjects()).build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable Long id,
			@Valid @RequestBody ProjectRequest request) {

		return ResponseEntity.ok(ApiResponse.<ProjectResponse>builder().success(true)
				.message("Project updated successfully").data(projectService.updateProject(id, request)).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteProject(@PathVariable Long id) {

		projectService.deleteProject(id);

		return ResponseEntity.ok(ApiResponse.<String>builder().success(true).message("Project deleted successfully")
				.data("Deleted Successfully").build());
	}

}