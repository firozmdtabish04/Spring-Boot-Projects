package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DesignationRequest {

	@NotBlank
	private String designationName;

	private String grade;

	private String description;

}