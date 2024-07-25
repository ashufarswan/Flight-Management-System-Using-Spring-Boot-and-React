package com.microservice.flights.controller;
 
 
 
import com.microservice.flights.models.ApiResponse;
import com.microservice.flights.models.Flight;
import com.microservice.flights.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
 
class FlightControllerTest {
 
    @InjectMocks
    private FlightController flightController;
 
    @Mock
    private FlightService flightService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllFlightDetails() {
        List<Flight> flights = Arrays.asList(new Flight(1, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now(), 300L, "Delta", "Commercial", 500.0, null));
        when(flightService.getAllFlightDetails()).thenReturn(flights);
 
        ResponseEntity<ApiResponse<List<Flight>>> response = flightController.getAllFlightDetails(null, null, null, null, null, null);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals(flights, response.getBody().getData());
    }
 
    @Test
    void testGetFlightById() {
        Flight flight = new Flight(1, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now(), 300L, "Delta", "Commercial", 500.0, null);
        when(flightService.getFlight(1)).thenReturn(flight);
 
        ResponseEntity<ApiResponse<Flight>> response = flightController.getFlightById(1);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals(flight, response.getBody().getData());
    }
 
    @Test
    void testAddFlight() {
        Flight flight = new Flight(1, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now(), 300L, "Delta", "Commercial", 500.0, null);
        when(flightService.addFlight(any(Flight.class))).thenReturn(flight);
 
        ResponseEntity<ApiResponse<Flight>> response = flightController.addFlight(flight);
 
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals(flight, response.getBody().getData());
    }
 
    @Test
    void testUpdateFlight() {
        Flight flight = new Flight(1, "NYC", "LAX", LocalDateTime.now(), LocalDateTime.now(), 300L, "Delta", "Commercial", 500.0, null);
        when(flightService.updateFlight(eq(1), any(Flight.class))).thenReturn(flight);
 
        ResponseEntity<ApiResponse<Flight>> response = flightController.updateFlight(1, flight);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals(flight, response.getBody().getData());
    }
 
    @Test
    void testDeleteFlight() {
        doNothing().when(flightService).deleteFlight(1);
 
        ResponseEntity<ApiResponse<?>> response = flightController.deleteFlight(1);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
    }
}