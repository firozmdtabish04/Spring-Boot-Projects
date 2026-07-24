package com.example.demo.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.Otp;
import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.service.interfaces.AuthService;
import com.example.demo.service.interfaces.OtpService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final OtpService otpService;
	private final OtpRepository otpRepository;

	@Override
	public AuthResponse register(RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}

		User user = User.builder().name(request.getName()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).build();

		userRepository.save(user);

		String token = jwtService.generateToken(user.getEmail());

		return AuthResponse.builder().token(token).message("User registered successfully").build();
	}

	@Override
	public AuthResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		String token = jwtService.generateToken(user.getEmail());

		return AuthResponse.builder().token(token).message("Login successful").build();
	}

	@Override
	public void forgotPassword(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		otpService.sendOtp(user.getEmail());
	}

	@Override
	public void resetPassword(String email, String newPassword, String confirmPassword) {

		if (!newPassword.equals(confirmPassword)) {
			throw new IllegalArgumentException("Passwords do not match.");
		}

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		Otp otp = otpRepository.findTopByEmailOrderByCreatedAtDesc(email)
				.orElseThrow(() -> new ResourceNotFoundException("OTP not found."));

		if (!Boolean.TRUE.equals(otp.getVerified())) {
			throw new IllegalStateException("OTP is not verified.");
		}

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);

		// Prevent OTP reuse
		otpRepository.delete(otp);
	}
}