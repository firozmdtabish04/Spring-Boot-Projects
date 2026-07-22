package com.example.demo.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.EmailRequest;
import com.example.demo.service.interfaces.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Override
	public void sendEmail(EmailRequest request) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(request.getTo());
		message.setSubject(request.getSubject());
		message.setText(request.getBody());

		mailSender.send(message);
	}
}