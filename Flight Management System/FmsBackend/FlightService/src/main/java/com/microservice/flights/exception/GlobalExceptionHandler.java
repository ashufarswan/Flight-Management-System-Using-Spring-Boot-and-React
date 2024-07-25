package com.microservice.flights.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.flights.models.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(FlightNotFoundException.class)
	public ResponseEntity<Map<String, String>> JobNotFoundExceptionHandler(FlightNotFoundException e){
		Map<String, String> errorMessage = new HashMap<>();
		errorMessage.put("error", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
				
	}
	@ExceptionHandler(FlightAlreadyExistException.class)
	public ResponseEntity<Map<String, String>> JobPostAlreadyExistsExceptionHandler(FlightAlreadyExistException e){
		Map<String, String> errorMessage = new HashMap<>();
		errorMessage.put("error", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
				
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleIllegalFieldValueException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errorMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName = ( (FieldError)error).getField();
			String msg = error.getDefaultMessage();
			
			errorMap.put(fieldName, msg);
			
		});
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);	
	}
}
