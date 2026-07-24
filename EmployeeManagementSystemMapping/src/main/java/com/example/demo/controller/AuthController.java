package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ForgotPasswordRequest;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.request.ResetPasswordRequest;
import com.example.demo.dto.request.VerifyOtpRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.service.interfaces.AuthService;
import com.example.demo.service.interfaces.OtpService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final OtpService otpService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

		AuthResponse response = authService.register(request);

		return ApiResponse.success("Registration successful", response);
	}

	@PostMapping("/login")
	public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

		return ApiResponse.success("Login successful", authService.login(request));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {

		authService.forgotPassword(request.getEmail());

		return ResponseEntity.ok("Password reset OTP sent successfully.");
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

		authService.resetPassword(request.getEmail(), request.getNewPassword(), request.getConfirmPassword());

		return ResponseEntity.ok("Password reset successfully.");
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {

		boolean verified = otpService.verifyOtp(request.getEmail(), request.getOtp());

		if (!verified) {
			return ResponseEntity.badRequest().body("Invalid or expired OTP.");
		}

		return ResponseEntity.ok("OTP verified successfully.");
	}
}