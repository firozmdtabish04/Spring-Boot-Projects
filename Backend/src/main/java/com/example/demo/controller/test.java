package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
	@RequestMapping("/")
	public String Name() {
		return "Tabis";
	}
}
