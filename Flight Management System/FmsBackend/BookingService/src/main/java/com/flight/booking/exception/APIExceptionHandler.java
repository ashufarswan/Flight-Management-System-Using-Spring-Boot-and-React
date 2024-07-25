package com.flight.booking.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flight.booking.models.ApiResponse;
import com.flight.booking.models.Booking;



/*
 * Global Exception Handler
 * */

@RestControllerAdvice
public class APIExceptionHandler {
	
	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(RuntimeException exception) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ApiResponse<Booking>("failed", exception.getMessage(), null )
				);
	}
	
	@ExceptionHandler(DuplicateBookingException.class)
	public ResponseEntity<ApiResponse<?>>  handleDuplicateResourceException(RuntimeException exception) {
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				new ApiResponse<Booking>("failed", exception.getMessage(), null )
				);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleIllegalFieldValueException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errorMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName = ( (FieldError)error).getField();
			String msg = error.getDefaultMessage();
			
			errorMap.put(fieldName, msg);
			
		});
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ApiResponse<Map<String, String>>("error", "Invalid Inputs", errorMap )
				);	
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleInternalServerException(Exception exception){
		exception.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ApiResponse<Booking>("error", "Something Went Wrong : " + exception.getMessage(), null)
				);
				
	}
}

