package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class LogTestController {

	private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);

	@GetMapping("/logs")
	public String testLogs() {

		logger.debug("Debug log");

		logger.info("Info log");

		logger.warn("Warning log");

		logger.error("Error log");

		return "Logs Generated";
	}
}