package com.example.demo.notification;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailNotification implements NotificationService {
	@Override
	public void sendNotification(String message) {
		System.out.println("Email Sent : " + message);
	}

}
