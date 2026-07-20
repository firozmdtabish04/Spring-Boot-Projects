package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import com.example.demo.utility.Company;
import com.example.demo.utility.ReportGenerator;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
@PropertySource("classpath:application.properties")
public class AppConfig {

	@Value("${company.name}")
	private String companyName;

	@Value("${company.location}")
	private String companyLocation;

	@Bean
	public Company company() {

		return new Company(companyName, companyLocation);
	}

	@Bean
	@Lazy
	public ReportGenerator reportGenerator() {
		return new ReportGenerator();
	}

}
