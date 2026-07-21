package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.request.EmployeeRequest;
import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.entity.Department;
import com.example.demo.entity.Designation;
import com.example.demo.entity.Employee;

@Component
public class EmployeeMapper {

	public Employee toEntity(EmployeeRequest request, Department department) {

		return Employee.builder().name(request.getName()).email(request.getEmail()).phone(request.getPhone())
				.salary(request.getSalary()).joiningDate(request.getJoiningDate()).gender(request.getGender())
				.status(request.getStatus()).department(department).build();

	}

	public EmployeeResponse toResponse(Employee employee) {

		return EmployeeResponse.builder().id(employee.getId()).name(employee.getName()).email(employee.getEmail())
				.phone(employee.getPhone()).salary(employee.getSalary()).joiningDate(employee.getJoiningDate())
				.gender(employee.getGender()).status(employee.getStatus())
				.departmentName(employee.getDepartment().getDepartmentName()).build();

	}

	public Employee toEntity(EmployeeRequest request, Department department, Designation designation) {

		return Employee.builder().name(request.getName()).email(request.getEmail()).phone(request.getPhone())
				.salary(request.getSalary()).joiningDate(request.getJoiningDate()).gender(request.getGender())
				.status(request.getStatus()).department(department).designation(designation).build();

	}

}