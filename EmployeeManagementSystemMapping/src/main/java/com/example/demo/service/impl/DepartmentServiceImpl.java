package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.DepartmentRequest;
import com.example.demo.dto.response.DepartmentResponse;
import com.example.demo.entity.Department;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.interfaces.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;

	@Override
	public DepartmentResponse createDepartment(DepartmentRequest request) {

		if (departmentRepository.existsByDepartmentName(request.getDepartmentName())) {
			throw new DuplicateResourceException("Department already exists.");
		}

		Department department = departmentMapper.toEntity(request);

		return departmentMapper.toResponse(departmentRepository.save(department));
	}

	@Override
	public DepartmentResponse getDepartmentById(Long id) {

		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found."));

		return departmentMapper.toResponse(department);
	}

	@Override
	public List<DepartmentResponse> getAllDepartments() {

		return departmentRepository.findAll().stream().map(departmentMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found."));

		department.setDepartmentName(request.getDepartmentName());
		department.setLocation(request.getLocation());

		return departmentMapper.toResponse(departmentRepository.save(department));
	}

	@Override
	public void deleteDepartment(Long id) {

		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found."));

		departmentRepository.delete(department);

	}

}