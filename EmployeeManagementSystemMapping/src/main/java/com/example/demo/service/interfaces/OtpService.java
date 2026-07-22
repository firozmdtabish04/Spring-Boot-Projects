package com.example.demo.service.interfaces;

public interface OtpService {

	void sendOtp(String email);

	boolean verifyOtp(String email, String otp);

}
