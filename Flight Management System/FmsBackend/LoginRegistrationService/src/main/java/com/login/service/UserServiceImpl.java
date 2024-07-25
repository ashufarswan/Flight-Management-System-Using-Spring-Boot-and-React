package com.login.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.configuration.Jwtutil;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.AuthDto;
import com.login.model.User;
import com.login.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	Jwtutil jwtutil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean  registerUser(User user) throws UserAlreadyPresentException {
		
			String email = user.getEmail();
			String name = user.getName();
			User u = userRepository.findByNameOrEmail(name, email);
			if(u != null) {
				logger.info("User already exists...");
				throw new UserAlreadyPresentException("Can't add user. Already exits. Please change user name or email");
			}
			else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setRole("USER");
				userRepository.save(user);
				return true;
			}
	}

	@Override
	public String genrateUniqueRandomId() {
		Random random = new Random();
		String id = String.valueOf(random.nextInt(999999)+1);
		
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			id = genrateUniqueRandomId();
		}
		
		return id;
	}

	@Override
	public String login(AuthDto loginUser) throws InvalidCredentialException {
		
 	   String str = null;
 	   try {
	 	   Authentication authentication =  authenticationManager.authenticate(
	 			   new UsernamePasswordAuthenticationToken(
	                        loginUser.getUserName(),
	                        loginUser.getPassword()
	                ));
	 	   if(authentication.isAuthenticated()) {
	 		   User u = userRepository.findByNameOrEmail(loginUser.getUserName(), loginUser.getUserName());
	 		   str = jwtutil.generateToken(u.getName(),u.getRole(),u.getId(),u.getEmail());
	 	   	   return str;
	 	   }else {
	 		  throw new InvalidCredentialException("Invalid user name or password");
	 	   }
 	   }
 	   catch(BadCredentialsException e) {
 		   throw new InvalidCredentialException("Invalid user name or password");
 	   }
	}

	@Override
	public User getUser(String userId) throws UserNotFoundException{
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException("Invalid User Id");
		}
		return user.get();
	}
	
	
	
	

}
