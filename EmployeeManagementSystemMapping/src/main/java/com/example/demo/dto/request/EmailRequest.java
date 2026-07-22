package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {

	@Email
	@NotBlank
	private String to;

	@NotBlank
	private String subject;

	@NotBlank
	private String body;
}