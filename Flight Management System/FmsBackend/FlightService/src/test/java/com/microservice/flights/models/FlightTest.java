package com.microservice.flights.models;
 
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
 
class FlightTest {
 
    @Test
    void testFlightConstructorAndGetters() {
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(5);
        Flight flight = new Flight(1, "NYC", "LAX", departureTime, arrivalTime, 300L, "Delta", "Commercial", 500.0, Arrays.asList(1, 2, 3));
 
        assertEquals(1, flight.getFlightId());
        assertEquals("NYC", flight.getDeparture());
        assertEquals("LAX", flight.getDestination());
        assertEquals(departureTime, flight.getDepartureDateAndTime());
        assertEquals(arrivalTime, flight.getArrivalDateAndTime());
        assertEquals(300L, flight.getDuration());
        assertEquals("Delta", flight.getAirline());
        assertEquals("Commercial", flight.getAirlineType());
        assertEquals(500.0, flight.getPrice());
        assertEquals(Arrays.asList(1, 2, 3), flight.getBookingIdList());
    }
 
    @Test
    void testFlightSetters() {
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(5);
        Flight flight = new Flight();
 
        flight.setFlightId(1);
        flight.setDeparture("NYC");
        flight.setDestination("LAX");
        flight.setDepartureDateAndTime(departureTime);
        flight.setArrivalDateAndTime(arrivalTime);
        flight.setDuration(300L);
        flight.setAirline("Delta");
        flight.setAirlineType("Commercial");
        flight.setPrice(500.0);
        flight.setBookingIdList(Arrays.asList(1, 2, 3));
 
        assertEquals(1, flight.getFlightId());
        assertEquals("NYC", flight.getDeparture());
        assertEquals("LAX", flight.getDestination());
        assertEquals(departureTime, flight.getDepartureDateAndTime());
        assertEquals(arrivalTime, flight.getArrivalDateAndTime());
        assertEquals(300L, flight.getDuration());
        assertEquals("Delta", flight.getAirline());
        assertEquals("Commercial", flight.getAirlineType());
        assertEquals(500.0, flight.getPrice());
        assertEquals(Arrays.asList(1, 2, 3), flight.getBookingIdList());
    }
 
    @Test
    void testToString() {
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(5);
        Flight flight = new Flight(1, "NYC", "LAX", departureTime, arrivalTime, 300L, "Delta", "Commercial", 500.0, Arrays.asList(1, 2, 3));
 
        String expectedString = "Flight(flightId=1, departure=NYC, destination=LAX, departureDateAndTime=" 
                                + departureTime + ", arrivalDateAndTime=" + arrivalTime + ", duration=300, airline=Delta, airlineType=Commercial, price=500.0, bookingIdList=[1, 2, 3])";
        assertEquals(expectedString, flight.toString());
    }
 
}