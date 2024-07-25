package com.microservice.flights.service;

import java.util.List;

import com.microservice.flights.models.Flight;

public interface FlightService {
	
	List<Flight> getAllFlightDetails();
	Flight addFlight(Flight flight);
	
	Flight getFlight(Integer flightId);
	
	Flight updateFlight(Integer flightId, Flight flight);
	
	boolean deleteFlight(int flightId);
	
	// methods for Booking Service 
	 Flight getFlightByBookingId(Integer bookingId );
	 
	 Flight postBookingIdToFlight(Integer flightId,Integer bookingId) ;
	 
	 void deleteBookingIdFromFlight(Integer flightId, Integer bookingId); 
	
}
