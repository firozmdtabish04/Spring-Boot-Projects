package com.example.demo.service.interfaces;

import com.example.demo.dto.request.EmailRequest;

public interface EmailService {

	void sendEmail(EmailRequest request);

}