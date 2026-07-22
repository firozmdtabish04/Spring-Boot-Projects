package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.OtpRequest;
import com.example.demo.dto.request.OtpVerificationRequest;
import com.example.demo.service.interfaces.OtpService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

	private final OtpService otpService;

	@PostMapping("/send")
	public ResponseEntity<String> sendOtp(@Valid @RequestBody OtpRequest request) {

		otpService.sendOtp(request.getEmail());

		return ResponseEntity.ok("OTP sent successfully.");
	}

	@PostMapping("/verify")
	public ResponseEntity<String> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {

		boolean verified = otpService.verifyOtp(request.getEmail(), request.getOtp());

		if (verified) {
			return ResponseEntity.ok("OTP verified successfully.");
		}

		return ResponseEntity.badRequest().body("Invalid or expired OTP.");
	}
}
