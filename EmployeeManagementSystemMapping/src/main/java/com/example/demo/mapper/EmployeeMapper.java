package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.request.AddressRequest;
import com.example.demo.dto.request.EmployeeRequest;
import com.example.demo.dto.response.AddressResponse;
import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.entity.Address;
import com.example.demo.entity.Department;
import com.example.demo.entity.Designation;
import com.example.demo.entity.Employee;

@Component
public class EmployeeMapper {

	// Old mapper (optional, can be removed if unused)
	public Employee toEntity(EmployeeRequest request, Department department) {

		return Employee.builder().name(request.getName()).email(request.getEmail()).phone(request.getPhone())
				.salary(request.getSalary()).joiningDate(request.getJoiningDate()).gender(request.getGender())
				.status(request.getStatus()).department(department).build();
	}

	// New mapper with Department + Designation + Address
	public Employee toEntity(EmployeeRequest request, Department department, Designation designation) {

		AddressRequest addressRequest = request.getAddress();

		Address address = Address.builder().houseNo(addressRequest.getHouseNo()).street(addressRequest.getStreet())
				.city(addressRequest.getCity()).state(addressRequest.getState()).country(addressRequest.getCountry())
				.pincode(addressRequest.getPincode()).build();

		Employee employee = Employee.builder().name(request.getName()).email(request.getEmail())
				.phone(request.getPhone()).salary(request.getSalary()).joiningDate(request.getJoiningDate())
				.gender(request.getGender()).status(request.getStatus()).department(department).designation(designation)
				.address(address).build();

		address.setEmployee(employee);

		return employee;
	}

	public EmployeeResponse toResponse(Employee employee) {

		AddressResponse addressResponse = null;

		if (employee.getAddress() != null) {
			addressResponse = AddressResponse.builder().id(employee.getAddress().getId())
					.houseNo(employee.getAddress().getHouseNo()).street(employee.getAddress().getStreet())
					.city(employee.getAddress().getCity()).state(employee.getAddress().getState())
					.country(employee.getAddress().getCountry()).pincode(employee.getAddress().getPincode()).build();
		}

		return EmployeeResponse.builder().id(employee.getId()).name(employee.getName()).email(employee.getEmail())
				.phone(employee.getPhone()).salary(employee.getSalary()).joiningDate(employee.getJoiningDate())
				.gender(employee.getGender()).status(employee.getStatus())
				.departmentName(employee.getDepartment().getDepartmentName())
				.designationName(employee.getDesignation().getDesignationName()).address(addressResponse).build();
	}
}