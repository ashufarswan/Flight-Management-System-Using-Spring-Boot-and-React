package com.login.service;

import org.springframework.http.ResponseEntity;

import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.AuthDto;
import com.login.model.User;

public interface UserService {

	public Boolean registerUser(User user) throws UserAlreadyPresentException;

	public String genrateUniqueRandomId();
	
	public String login(AuthDto loginUser) throws InvalidCredentialException;

	User getUser(String userId) throws UserNotFoundException;
}
