package com.example.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public AuthResponse register(RegisterRequest request) {

		logger.info("Registration request received for email: {}", request.getEmail());

		if (userRepository.existsByEmail(request.getEmail())) {

			logger.warn("Registration failed. Email already exists: {}", request.getEmail());

			throw new DuplicateResourceException("Email already exists");
		}

		logger.debug("Encoding password for email: {}", request.getEmail());

		User user = User.builder().name(request.getName()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).build();

		userRepository.save(user);

		logger.info("User registered successfully. User ID: {}", user.getId());

		String token = jwtService.generateToken(user.getEmail());

		logger.info("JWT token generated for {}", user.getEmail());

		return AuthResponse.builder().token(token).message("User registered successfully").build();
	}

	@Override
	public AuthResponse login(LoginRequest request) {

		logger.info("Login attempt for email: {}", request.getEmail());

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {

			logger.error("Login failed. User not found: {}", request.getEmail());

			return new ResourceNotFoundException("User not found");
		});

		String token = jwtService.generateToken(user.getEmail());

		logger.info("User logged in successfully: {}", user.getEmail());

		return AuthResponse.builder().token(token).message("Login successful").build();
	}

	@Override
	public void forgotPassword(String email) {

		logger.info("Forgot password request received for {}", email);

		User user = userRepository.findByEmail(email).orElseThrow(() -> {

			logger.error("Forgot password failed. User not found: {}", email);

			return new ResourceNotFoundException("User not found with email: " + email);
		});

		otpService.sendOtp(user.getEmail());

		logger.info("Password reset OTP sent to {}", email);
	}

	@Override
	public void resetPassword(String email, String newPassword, String confirmPassword) {

		logger.info("Password reset started for {}", email);

		if (!newPassword.equals(confirmPassword)) {

			logger.warn("Password mismatch for {}", email);

			throw new IllegalArgumentException("Passwords do not match.");
		}

		User user = userRepository.findByEmail(email).orElseThrow(() -> {

			logger.error("Password reset failed. User not found: {}", email);

			return new ResourceNotFoundException("User not found with email: " + email);
		});

		Otp otp = otpRepository.findTopByEmailOrderByCreatedAtDesc(email).orElseThrow(() -> {

			logger.error("OTP not found for {}", email);

			return new ResourceNotFoundException("OTP not found.");
		});

		if (!Boolean.TRUE.equals(otp.getVerified())) {

			logger.warn("OTP not verified for {}", email);

			throw new IllegalStateException("OTP is not verified.");
		}

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);

		otpRepository.delete(otp);

		logger.info("Password updated successfully for {}", email);
	}
}