package com.example.demo.exception;

/* Runtime exception must be explicitly specified Other wise Handle the exception there only 
 * Runtime exception is not mandatory to be handled*/

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message );
		
	}

}
