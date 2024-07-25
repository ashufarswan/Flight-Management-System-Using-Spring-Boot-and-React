package com.flight.booking.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.flight.booking.models.Flight;
import com.flight.booking.models.Passenger;

@FeignClient(url = "http://localhost:8083/api", value = "Passenger-Client")
public interface PassengerClient {
	
	@GetMapping("/passenger/booking/{bookingId}")
	public List<Passenger> getPassengersByBookingId(@PathVariable("bookingId") Integer bookingId ) ;
	
	@PostMapping("/passenger/{passengerId}/booking/{bookingId}")
	public Passenger postBookingIdToPassengerService(@PathVariable("passengerId") Integer passengerId ,@PathVariable("bookingId") Integer bookingId) ;
	
	@DeleteMapping("/passenger/{passengerId}/booking/{bookingId}")
	public void deleteBookingIdFromPassenger(@PathVariable("passengerId") Integer passengerId, @PathVariable("bookingId") Integer bookingId);

}