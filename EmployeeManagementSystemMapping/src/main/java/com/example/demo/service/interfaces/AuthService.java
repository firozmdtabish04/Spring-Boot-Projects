package com.example.demo.service.interfaces;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;

public interface AuthService {

	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

	void forgotPassword(String email);

	void resetPassword(String email, String newPassword, String confirmPassword);

}