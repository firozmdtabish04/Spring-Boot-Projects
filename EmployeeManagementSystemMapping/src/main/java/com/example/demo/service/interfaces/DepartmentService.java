package com.example.demo.service.interfaces;

import java.util.List;

import com.example.demo.dto.request.DepartmentRequest;
import com.example.demo.dto.response.DepartmentResponse;

public interface DepartmentService {

	DepartmentResponse createDepartment(DepartmentRequest request);

	DepartmentResponse getDepartmentById(Long id);

	List<DepartmentResponse> getAllDepartments();

	DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

	void deleteDepartment(Long id);

}