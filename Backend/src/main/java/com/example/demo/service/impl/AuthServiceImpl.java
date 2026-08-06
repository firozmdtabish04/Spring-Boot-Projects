package com.example.demo.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.RoleType;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	@Override
	public AuthResponse register(RegisterRequest request) {

		if (repository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		User user = User.builder().fullName(request.getFullName()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(RoleType.ROLE_USER).build();

		repository.save(user);

		String token = jwtService.generateToken(user.getEmail());

		return AuthResponse.builder().token(token).email(user.getEmail()).fullName(user.getFullName())
				.role(user.getRole().name()).build();
	}

	@Override
	public AuthResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = repository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));

		String token = jwtService.generateToken(user.getEmail());

		return AuthResponse.builder().token(token).email(user.getEmail()).fullName(user.getFullName())
				.role(user.getRole().name()).build();
	}
}
