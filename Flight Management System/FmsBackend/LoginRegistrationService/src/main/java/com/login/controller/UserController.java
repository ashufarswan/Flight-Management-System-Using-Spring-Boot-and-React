package com.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.login.configuration.Jwtutil;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.AuthDto;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.service.CustomUserDetailsService;
import com.login.service.UserServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private Jwtutil jwtutil;
	
		
	@PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) throws UserAlreadyPresentException {
            user.setId(userService.genrateUniqueRandomId());
            if(userService.registerUser(user)) {
            		return new ResponseEntity<String>("Customer added successfully!!!",HttpStatus.OK);
            }else {
            	return new ResponseEntity<String>("Something went worng!!!",HttpStatus.INTERNAL_SERVER_ERROR);
            }
	}
        

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid AuthDto loginUser) throws InvalidCredentialException {
    	   return ResponseEntity.ok(userService.login(loginUser));
    }


    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) throws UserNotFoundException{
    	return new ResponseEntity<User> (userService.getUser(userId),HttpStatus.OK);
    }
   

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestParam("token") String token) {
        jwtutil.validateToken(token);
        return ResponseEntity.ok("Token is valid");
    }
}
