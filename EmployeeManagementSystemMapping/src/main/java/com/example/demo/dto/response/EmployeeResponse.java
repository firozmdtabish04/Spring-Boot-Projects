package com.example.demo.dto.response;

import java.time.LocalDate;

import com.example.demo.enums.EmployeeStatus;
import com.example.demo.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {

	private Long id;

	private String name;

	private String email;

	private String phone;

	private Double salary;

	private LocalDate joiningDate;

	private Gender gender;

	private EmployeeStatus status;

	private String departmentName;

	private String designationName;
	private AddressResponse address;

}