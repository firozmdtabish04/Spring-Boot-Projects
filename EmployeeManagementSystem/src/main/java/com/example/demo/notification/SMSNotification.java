package com.example.demo.notification;

import org.springframework.stereotype.Component;

@Component
public class SMSNotification implements NotificationService {

	@Override
	public void sendNotification(String message) {
		System.out.println("SMS Sent : " + message);
	}
}