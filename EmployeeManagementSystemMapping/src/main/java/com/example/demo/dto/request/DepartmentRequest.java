package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequest {

	@NotBlank(message = "Department name is required")
	private String departmentName;

	@NotBlank(message = "Location is required")
	private String location;

}