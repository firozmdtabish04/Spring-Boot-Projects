package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.EmailRequest;
import com.example.demo.service.interfaces.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;

	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest request) {

		emailService.sendEmail(request);

		return ResponseEntity.ok("Email sent successfully");
	}
}