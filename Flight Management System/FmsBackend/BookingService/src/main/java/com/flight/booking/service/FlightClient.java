package com.flight.booking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.flight.booking.models.Flight;

@FeignClient(url = "http://localhost:8081/api", value = "Flight-Client")
public interface FlightClient {
	
	@GetMapping("/flight/booking/{bookingId}")
	public Flight getFlightByBookingId(@PathVariable("bookingId") Integer bookingId ) ;
	
	@PostMapping("/flight/{flightId}/booking/{bookingId}")
	public Flight postBookingIdToFlightService(@PathVariable("flightId") Integer flightId, @PathVariable("bookingId") Integer bookingId) ;
	
	@DeleteMapping("/flight/{flightId}/booking/{bookingId}")
	public void deleteBookingIdFromFlight(@PathVariable("flightId") Integer flightId, @PathVariable("bookingId") Integer bookingId); 
}

