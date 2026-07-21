package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.request.DepartmentRequest;
import com.example.demo.dto.response.DepartmentResponse;
import com.example.demo.entity.Department;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {

	private final ModelMapper modelMapper;

	public Department toEntity(DepartmentRequest request) {
		return modelMapper.map(request, Department.class);
	}

	public DepartmentResponse toResponse(Department department) {
		return modelMapper.map(department, DepartmentResponse.class);
	}

}