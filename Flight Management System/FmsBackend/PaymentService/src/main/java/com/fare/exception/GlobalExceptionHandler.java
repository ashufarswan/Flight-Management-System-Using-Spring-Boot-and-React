package com.fare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.razorpay.RazorpayException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidIdException.class)
	ResponseEntity<String> handleInvalidIdException(InvalidIdException e,WebRequest req){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BookingStatusDoneException.class)
	ResponseEntity<String> handleBookingException(BookingStatusDoneException e,WebRequest req){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(RazorpayException.class)
	ResponseEntity<String> handleBookingException(RazorpayException e,WebRequest req){
		return new ResponseEntity<String>("Payment Failed",HttpStatus.REQUEST_TIMEOUT);
	}
		
	
	@ExceptionHandler(Exception.class)
	ResponseEntity<String> handleGlobalException(Exception e,WebRequest req){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
