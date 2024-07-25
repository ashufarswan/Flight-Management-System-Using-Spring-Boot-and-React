package com.microservice.flights.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservice.flights.exception.FlightAlreadyExistException;
import com.microservice.flights.exception.FlightNotFoundException;
import com.microservice.flights.models.Flight;
import com.microservice.flights.repo.FlightRepo;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {

    @Mock
    private FlightRepo mockRepo;

    @InjectMocks
    private FlightServiceImpl flightService;

    private Flight sampleFlight;

    @BeforeEach
    public void setUp() {
        sampleFlight = new Flight();
        sampleFlight.setFlightId(1);
        sampleFlight.setDeparture("Airport A");
        sampleFlight.setDestination("Airport B");
        sampleFlight.setDuration(120L);
        sampleFlight.setAirline("Sample Airlines");
        sampleFlight.setAirlineType("International");
        sampleFlight.setPrice(1000.0);
        sampleFlight.setBookingIdList(new ArrayList<>());
    }

    @Test
    public void testAddFlight_NewFlight_Success() {
        // Mock repository behavior for save
        when(mockRepo.save(any(Flight.class))).thenReturn(sampleFlight);

        // Call the service method
        Flight savedFlight = flightService.addFlight(sampleFlight);

        // Verify the saved flight
        assertEquals(sampleFlight.getFlightId(), savedFlight.getFlightId());
        assertEquals(sampleFlight.getDeparture(), savedFlight.getDeparture());
        // Add assertions for other fields
    }

    @Test
    public void testAddFlight_ExistingFlight_FlightAlreadyExistException() {
        // Mock repository behavior for findById
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));

        // Call the service method that should throw exception
        FlightAlreadyExistException exception = assertThrows(FlightAlreadyExistException.class, () -> {
            flightService.addFlight(sampleFlight);
        });

        assertEquals("1 Flight ID already exist", exception.getMessage());
    }

    @Test
    public void testGetFlight_ExistingFlight_Success() {
        // Mock repository behavior for findById
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));

        // Call the service method
        Flight fetchedFlight = flightService.getFlight(1);

        // Verify the fetched flight
        assertEquals(sampleFlight.getFlightId(), fetchedFlight.getFlightId());
        assertEquals(sampleFlight.getDeparture(), fetchedFlight.getDeparture());

    }

    @Test
    public void testGetFlight_NonExistingFlight_FlightNotFoundException() {
        // Mock repository behavior for findById
        when(mockRepo.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> {
            flightService.getFlight(999);
        });

        assertEquals("999 Flight ID does not exist", exception.getMessage());
    }

    @Test
    public void testUpdateFlight_ExistingFlight_Success() {
        // Mock repository behavior for findById and save
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));
        when(mockRepo.save(any(Flight.class))).thenReturn(sampleFlight);

        // Create updated details
        Flight updatedFlight = new Flight();
        updatedFlight.setDeparture("Airport C");
        updatedFlight.setDestination("Airport D");

        // Call the service method
        Flight result = flightService.updateFlight(1, updatedFlight);

        // Verify the updated flight
        assertEquals(updatedFlight.getDeparture(), result.getDeparture());
        assertEquals(updatedFlight.getDestination(), result.getDestination());
        // Add assertions for other fields
    }

    @Test
    public void testUpdateFlight_NonExistingFlight_FlightNotFoundException() {
        // Mock repository behavior for findById
        when(mockRepo.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> {
            flightService.updateFlight(999, new Flight());
        });

        assertEquals("999 Flight ID does not exist", exception.getMessage());
    }

    @Test
    public void testDeleteFlight_ExistingFlight_Success() {
        // Mock repository behavior for findById and deleteById
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));
        doNothing().when(mockRepo).deleteById(anyInt());

        // Call the service method
        flightService.deleteFlight(1);

       
    }

    @Test
    public void testDeleteFlight_NonExistingFlight_FlightNotFoundException() {
        // Mock repository behavior for findById
        when(mockRepo.findById(999)).thenReturn(Optional.empty());

        // Call the service method that should throw exception
        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> {
            flightService.deleteFlight(999);
        });

        assertEquals("999 Flight ID does not exist", exception.getMessage());
    }

    @Test
    public void testGetFlightByBookingId_ExistingBookingId_Success() {
        // Mock repository behavior for findByBookingId
        when(mockRepo.findByBookingId(123)).thenReturn(sampleFlight);

        // Call the service method
        Flight fetchedFlight = flightService.getFlightByBookingId(123);

        // Verify the fetched flight
        assertEquals(sampleFlight.getFlightId(), fetchedFlight.getFlightId());
        assertEquals(sampleFlight.getDeparture(), fetchedFlight.getDeparture());
    }

    @Test
    public void testPostBookingIdToFlight_AddBookingId_Success() {
        // Mock repository behavior for findById and save
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));
        when(mockRepo.save(any(Flight.class))).thenReturn(sampleFlight);

        // Call the service method
        Flight result = flightService.postBookingIdToFlight(1, 456);

        // Verify the updated flight
        assertEquals(1, result.getBookingIdList().size()); // Assuming there was already one booking ID
        assertEquals(Arrays.asList(456), result.getBookingIdList());
    }

    @Test
    public void testPostBookingIdToFlight_CreateNewBookingList_Success() {
        // Mock repository behavior for findById and save
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));
        when(mockRepo.save(any(Flight.class))).thenReturn(sampleFlight);
        sampleFlight.setBookingIdList(null); // Simulate null booking list

        // Call the service method
        Flight result = flightService.postBookingIdToFlight(1, 789);

        // Verify the updated flight
        assertEquals(1, result.getBookingIdList().size());
        assertEquals(Arrays.asList(789), result.getBookingIdList());
    }

    @Test
    public void testDeleteBookingIdFromFlight_ExistingBookingId_Success() {
        // Mock repository behavior for findById and save
        sampleFlight.setBookingIdList(Arrays.asList(123, 456));
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));
        when(mockRepo.save(any(Flight.class))).thenReturn(sampleFlight);

        // Call the service method
        flightService.deleteBookingIdFromFlight(1, 456);

        // Verify the updated flight
        assertEquals(2, sampleFlight.getBookingIdList().size());
        assertEquals(Arrays.asList(123, 456), sampleFlight.getBookingIdList());
    }

    @Test
    public void testDeleteBookingIdFromFlight_EmptyBookingList_Success() {
        // Mock repository behavior for findById and save
        sampleFlight.setBookingIdList(new ArrayList<>());
        when(mockRepo.findById(1)).thenReturn(Optional.of(sampleFlight));
        //when(mockRepo.save(any(Flight.class))).thenReturn(sampleFlight);

        // Call the service method
        flightService.deleteBookingIdFromFlight(1, 456);

        // Verify the updated flight (should remain empty)
        assertEquals(0, sampleFlight.getBookingIdList().size());
    }

    
}