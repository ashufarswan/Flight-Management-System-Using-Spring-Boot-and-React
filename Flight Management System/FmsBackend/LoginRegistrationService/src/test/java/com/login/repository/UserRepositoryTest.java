package com.login.repository;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.login.model.User;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Mocking the behavior of UserRepository
        User user = new User( "1","testuser", "test@example.com", "password", "1234567890", "USER",null,null);
        when(userRepository.findByName("testuser")).thenReturn(user);
    }

    @Test
    void testFindByName() {
        String username = "testuser";
        User found = userRepository.findByName(username);
        Assertions.assertEquals(username, found.getName());
    }

    

}
