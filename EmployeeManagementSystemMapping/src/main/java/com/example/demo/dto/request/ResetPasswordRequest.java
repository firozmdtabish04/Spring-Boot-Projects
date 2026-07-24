package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "New password is required")
	private String newPassword;

	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;

}