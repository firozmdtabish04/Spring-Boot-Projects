package com.example.demo.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.EmailRequest;
import com.example.demo.entity.Otp;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.OtpRepository;
import com.example.demo.service.interfaces.EmailService;
import com.example.demo.service.interfaces.OtpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OtpServiceImpl implements OtpService {

	private static final SecureRandom RANDOM = new SecureRandom();

	private final OtpRepository otpRepository;
	private final EmailService emailService;

	@Override
	public void sendOtp(String email) {

		// Invalidate previous OTP if it exists
		otpRepository.findTopByEmailOrderByCreatedAtDesc(email).ifPresent(existingOtp -> {
			existingOtp.setVerified(false);
			otpRepository.save(existingOtp);
		});

		String otp = generateOtp();

		Otp otpEntity = Otp.builder().email(email).otp(otp).verified(false).createdAt(LocalDateTime.now())
				.expiryTime(LocalDateTime.now().plusMinutes(5)).build();

		otpRepository.save(otpEntity);

		EmailRequest request = new EmailRequest();
		request.setTo(email);
		request.setSubject("OTP Verification");
		request.setBody("""
				Dear User,

				Your OTP is: %s

				This OTP is valid for 5 minutes.

				Do not share this OTP with anyone.

				Regards,
				Employee Management System
				""".formatted(otp));

		emailService.sendEmail(request);
	}

	@Override
	public boolean verifyOtp(String email, String otp) {

		Otp savedOtp = otpRepository.findTopByEmailOrderByCreatedAtDesc(email)
				.orElseThrow(() -> new ResourceNotFoundException("OTP not found."));

		if (savedOtp.getVerified()) {
			throw new BadRequestException("OTP already verified.");
		}

		if (savedOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new BadRequestException("OTP has expired.");
		}

		if (!savedOtp.getOtp().equals(otp)) {
			throw new BadRequestException("Invalid OTP.");
		}

		savedOtp.setVerified(true);
		otpRepository.save(savedOtp);

		return true;
	}

	private String generateOtp() {
		return String.format("%06d", RANDOM.nextInt(1_000_000));
	}
}