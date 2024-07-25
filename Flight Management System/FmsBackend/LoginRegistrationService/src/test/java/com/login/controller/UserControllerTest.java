package com.login.controller;

import com.login.controller.UserController;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.AuthDto;
import com.login.model.User;
import com.login.service.UserServiceImpl;
import com.login.configuration.Jwtutil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private Jwtutil jwtutil;

    @InjectMocks
    private UserController userController;

   
    @Test
    public void testRegisterUser_Success() throws UserAlreadyPresentException {
        // Mock data
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");
        user.setRole("USER");

        // Mock UserServiceImpl behavior
        when(userService.genrateUniqueRandomId()).thenReturn("uniqueId");
        when(userService.registerUser(any(User.class))).thenReturn(true);

        // Execute the controller method
        ResponseEntity<String> response = userController.registerUser(user);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer added successfully!!!", response.getBody());
    }

    @Test
    public void testRegisterUser_Failure() throws UserAlreadyPresentException {
        // Mock data
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");
        user.setRole("USER");

        // Mock UserServiceImpl behavior for failure case
        when(userService.genrateUniqueRandomId()).thenReturn("uniqueId");
        when(userService.registerUser(any(User.class))).thenReturn(false);

        // Execute the controller method
        ResponseEntity<String> response = userController.registerUser(user);

        // Verify the result
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went worng!!!", response.getBody());
    }

    @Test
    public void testLoginUser_Success() throws InvalidCredentialException {
        // Mock data
        AuthDto authDto = new AuthDto();
        authDto.setUserName("testUser");
        authDto.setPassword("testPassword");

        // Mock UserServiceImpl behavior
        when(userService.login(any(AuthDto.class))).thenReturn("mockedTokenString");

        // Execute the controller method
        ResponseEntity<String> response = userController.loginUser(authDto);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockedTokenString", response.getBody());
    }

    @Test
    public void testGetUser_Success() throws UserNotFoundException {
        // Mock data
        String userId = "userId123";
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("John Doe");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPassword("password");
        mockUser.setPhone("1234567890");
        mockUser.setRole("USER");

        // Mock UserServiceImpl behavior
        when(userService.getUser(userId)).thenReturn(mockUser);

        // Execute the controller method
        ResponseEntity<User> response = userController.getUser(userId);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    
    @Test
    public void testValidateToken_Success() {
        // Mock data
        String mockToken = "mockedToken";

        // Mock Jwtutil behavior (assuming validateToken does not throw exceptions upon valid token)
        doNothing().when(jwtutil).validateToken(mockToken);

        // Execute the controller method
        ResponseEntity<String> response = userController.validate(mockToken);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token is valid", response.getBody());
    }

}
