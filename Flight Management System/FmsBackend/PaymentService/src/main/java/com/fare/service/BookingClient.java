package com.fare.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fare.model.ApiResponse;
import com.fare.model.Booking;

@FeignClient(url = "http://localhost:8082/api", value = "Booking-Client")
public interface BookingClient {
	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<ApiResponse<Booking> > getBookingById(@PathVariable("bookingId") Integer bookingId); 

	@PutMapping("/booking/{bookingId}")
	public ResponseEntity<ApiResponse<Booking>> updateBooking(@PathVariable("bookingId") Integer bookingId, @RequestBody  Booking booking);
}
