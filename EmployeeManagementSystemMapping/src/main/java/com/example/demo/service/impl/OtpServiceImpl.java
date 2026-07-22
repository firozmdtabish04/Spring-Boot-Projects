package com.example.demo.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.EmailRequest;
import com.example.demo.entity.Otp;
import com.example.demo.repository.OtpRepository;
import com.example.demo.service.interfaces.EmailService;
import com.example.demo.service.interfaces.OtpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpServiceImpl implements OtpService {

	private final OtpRepository otpRepository;
	private final EmailService emailService;

	// Secure OTP Generator
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	@Override
	public void sendOtp(String email) {

		// Generate 6-digit OTP
		String otp = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));

		// Save OTP
		Otp otpEntity = Otp.builder().email(email).otp(otp).createdAt(LocalDateTime.now())
				.expiryTime(LocalDateTime.now().plusMinutes(5)).verified(false).build();

		otpRepository.save(otpEntity);

		// Send Email
		EmailRequest request = new EmailRequest();
		request.setTo(email);
		request.setSubject("OTP Verification");
		request.setBody("Hello,\n\n" + "Your OTP for verification is: " + otp + "\n\n"
				+ "This OTP is valid for 5 minutes.\n" + "Please do not share this OTP with anyone.\n\n" + "Regards,\n"
				+ "Tabish Firoz  Healthcare Management System ");

		emailService.sendEmail(request);
	}

	@Override
	public boolean verifyOtp(String email, String otp) {

		Optional<Otp> optionalOtp = otpRepository.findTopByEmailOrderByCreatedAtDesc(email);

		if (optionalOtp.isEmpty()) {
			return false;
		}

		Otp savedOtp = optionalOtp.get();

		// Already verified
		if (Boolean.TRUE.equals(savedOtp.getVerified())) {
			return false;
		}

		// OTP expired
		if (savedOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
			return false;
		}

		// OTP mismatch
		if (!savedOtp.getOtp().equals(otp)) {
			return false;
		}

		// Mark verified
		savedOtp.setVerified(true);
		otpRepository.save(savedOtp);

		return true;
	}
}