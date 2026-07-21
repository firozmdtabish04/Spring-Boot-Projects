package com.example.demo.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.EmployeeRequest;
import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.entity.Department;
import com.example.demo.entity.Designation;
import com.example.demo.entity.Employee;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DesignationRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.interfaces.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final DesignationRepository designationRepository;
	private final EmployeeMapper employeeMapper;

	@Override
	public EmployeeResponse createEmployee(EmployeeRequest request) {

		if (employeeRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already exists.");
		}

		if (employeeRepository.existsByPhone(request.getPhone())) {
			throw new DuplicateResourceException("Phone already exists.");
		}

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Department not found."));

		Designation designation = designationRepository.findById(request.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		Employee employee = employeeMapper.toEntity(request, department, designation);

		Employee savedEmployee = employeeRepository.save(employee);

		return employeeMapper.toResponse(savedEmployee);
	}

	@Override
	public EmployeeResponse getEmployeeById(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

		return employeeMapper.toResponse(employee);
	}

	@Override
	public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {

		return employeeRepository.findAll(pageable).map(employeeMapper::toResponse);
	}

	@Override
	public Page<EmployeeResponse> searchEmployees(String keyword, Pageable pageable) {

		return employeeRepository.findByNameContainingIgnoreCase(keyword, pageable).map(employeeMapper::toResponse);
	}

	@Override
	public Page<EmployeeResponse> getEmployeesByDepartment(Long departmentId, Pageable pageable) {

		if (!departmentRepository.existsById(departmentId)) {
			throw new ResourceNotFoundException("Department not found.");
		}

		return employeeRepository.findByDepartmentId(departmentId, pageable).map(employeeMapper::toResponse);
	}

	@Override
	public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Department not found."));

		Designation designation = designationRepository.findById(request.getDesignationId())
				.orElseThrow(() -> new ResourceNotFoundException("Designation not found."));

		if (!employee.getEmail().equals(request.getEmail()) && employeeRepository.existsByEmail(request.getEmail())) {

			throw new DuplicateResourceException("Email already exists.");
		}

		if (!employee.getPhone().equals(request.getPhone()) && employeeRepository.existsByPhone(request.getPhone())) {

			throw new DuplicateResourceException("Phone already exists.");
		}

		employee.setName(request.getName());
		employee.setEmail(request.getEmail());
		employee.setPhone(request.getPhone());
		employee.setSalary(request.getSalary());
		employee.setJoiningDate(request.getJoiningDate());
		employee.setGender(request.getGender());
		employee.setStatus(request.getStatus());
		employee.setDepartment(department);
		employee.setDesignation(designation);

		Employee updatedEmployee = employeeRepository.save(employee);

		return employeeMapper.toResponse(updatedEmployee);
	}

	@Override
	public void deleteEmployee(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found."));

		employeeRepository.delete(employee);
	}

	@Override
	public List<EmployeeResponse> getActiveEmployees() {

		return employeeRepository.findActiveEmployees().stream().map(employeeMapper::toResponse).toList();
	}

	@Override
	public List<EmployeeResponse> getEmployeesByDepartmentName(String departmentName) {

		return employeeRepository.findEmployeesByDepartment(departmentName).stream().map(employeeMapper::toResponse)
				.toList();
	}

	@Override
	public List<EmployeeResponse> getEmployeesWithSalaryGreaterThan(Double salary) {

		return employeeRepository.findEmployeesWithSalaryGreaterThan(salary).stream().map(employeeMapper::toResponse)
				.toList();
	}
}