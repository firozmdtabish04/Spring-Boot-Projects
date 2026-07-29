package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationRequest {

	@NotBlank(message = "Designation name is required")
	@Size(min = 2, max = 100, message = "Designation name must be between 2 and 100 characters")
	private String designationName;

	@Size(max = 300, message = "Description cannot exceed 300 characters")
	private String description;

}