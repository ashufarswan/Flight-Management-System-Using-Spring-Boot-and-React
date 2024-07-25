package com.example.demo.controller;
 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.Passenger;
import com.example.demo.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private PassengerService passengerService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private Passenger passenger;
 
    @BeforeEach
    public void setup() {
        passenger = new Passenger(1, "John", "Doe", "john.doe@example.com", "1234567890",
                new Date(), "American", "P1234567", "Male", "Aisle", Arrays.asList(1, 2, 3));
    }
 
    @Test
    public void testDeletePassenger1() throws Exception {
    	mockMvc.perform(delete("/api/passenger/1"))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.status").value("success"))
    	.andExpect(jsonPath("$.message").value("Deleted Passenger with passenger id 1"));
    }
   
 
    @Test
    public void testGetAllPassenger() throws Exception {
        when(passengerService.fetchAllPassengers()).thenReturn(Arrays.asList(passenger));
 
        mockMvc.perform(get("/api/passenger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("All Passengers fetched successfully"))
                .andExpect(jsonPath("$.data[0].firstName").value(passenger.getFirstName()));
    }
 
  
    
    @Test
    public void testDeletePassenger() throws Exception {
        mockMvc.perform(delete("/api/passenger/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Deleted Passenger with passenger id 1"));
    }
    
    @Test
    public void testGetAllPassengerNames() throws Exception {
        when(passengerService.fetchAllPassengers()).thenReturn(Arrays.asList(passenger));
 
        mockMvc.perform(get("/api/passenger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("All Passengers fetched successfully"))
                .andExpect(jsonPath("$.data[0].firstName").value(passenger.getFirstName()));
    }
    
}