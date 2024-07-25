package com.login.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.login.configuration.Jwtutil;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.model.AuthDto;
import com.login.model.User;
import com.login.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Jwtutil jwtutil;

    @InjectMocks
    private UserServiceImpl userService;

  
    @Test
    public void testRegisterUser_Success() throws UserAlreadyPresentException {
        // Mocking UserRepository behavior
       
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User user = new User();
        user.setName("TestUser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        boolean result = userService.registerUser(user);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

   

    @Test
    public void testGenerateUniqueRandomId() {
        // Mocking UserRepository behavior
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        String id = userService.genrateUniqueRandomId();

        assertNotNull(id);
        assertTrue(id.matches("\\d{1,6}")); // Check if the ID is a number up to 6 digits
    }

    
   


    @Test
    public void testLogin_InvalidCredentials() {
        // Mocking AuthenticationManager behavior to throw BadCredentialsException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        AuthDto authDto = new AuthDto();
        authDto.setUserName("testuser");
        authDto.setPassword("invalid_password");

        assertThrows(InvalidCredentialException.class, () -> {
            userService.login(authDto);
        });
    }
}