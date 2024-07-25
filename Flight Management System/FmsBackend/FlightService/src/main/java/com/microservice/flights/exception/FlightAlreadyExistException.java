package com.microservice.flights.exception;

public class FlightAlreadyExistException extends RuntimeException{
	
	 public FlightAlreadyExistException(String message) 
	 {
		 super(message);
	 }
}
