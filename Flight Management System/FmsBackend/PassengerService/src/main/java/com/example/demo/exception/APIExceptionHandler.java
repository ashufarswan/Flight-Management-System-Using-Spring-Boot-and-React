package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.models.ApiResponse;
import com.example.demo.models.Passenger;



/*
 * Global Exception Handler
 * */

@RestControllerAdvice
public class APIExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(RuntimeException exception) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ApiResponse<Passenger>("failed", exception.getMessage(), null )
				);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse<?>>  handleDuplicateResourceException(RuntimeException exception) {
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				new ApiResponse<Passenger>("failed", exception.getMessage(), null )
				);
	}
	
	
	@ExceptionHandler({HttpMessageConversionException.class, IllegalFieldValueException.class})
	public ResponseEntity<ApiResponse<?>> handleIllegalFieldValueException(RuntimeException exception) {
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ApiResponse<Passenger>("error", exception.getMessage(), null )
				);	
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse< Map<String, String> > > handleIllegalFieldValueException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errorMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName = ( (FieldError)error).getField();
			String msg = error.getDefaultMessage();
			
			errorMap.put(fieldName, msg);
			
		});
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ApiResponse<  Map<String, String> >("error", "Errors occured", errorMap )
				);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleInternalServerException(Exception exception){
		exception.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ApiResponse<Passenger>("error", "Something Went Wrong : " + exception.getMessage(), null)
				);
				
	}
	
	
}
