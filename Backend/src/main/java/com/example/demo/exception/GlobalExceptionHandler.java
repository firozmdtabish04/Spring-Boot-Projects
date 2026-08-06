package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse<Object>> duplicate(DuplicateResourceException ex) {

		return ResponseEntity.badRequest().body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> notFound(ResourceNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> generic(Exception ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.builder().success(false).message(ex.getMessage()).build());
	}

}
