package com.flight.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flight.booking.models.ApiResponse;
import com.flight.booking.models.Booking;
import com.flight.booking.models.Seat;
import com.flight.booking.service.BookingService;

import jakarta.validation.Valid;

/*
 * What will be the api input from postman
 * {
 * 		"bookingId" : 1, 
 * 		"numberOfPassengers" : ,
 * 		"seatList" : [
 * 			{
 * 				
 * 			},
 * 			{
 * 		
 * 			},
 * 		"bookingStatus" : true
 * 		]
 * 		"checkInId" : 	 
 * }
 * */

@RestController
@RequestMapping("/api")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	
	@PostMapping("/booking/flight/{flightId}")
	public ResponseEntity<ApiResponse<Booking> > createBooking(@PathVariable("flightId") Integer flightId, @RequestParam("passenger") List<String> passengers ,@RequestBody @Valid Booking booking) {
		//mention booking Id in Booking Object
		System.out.println(passengers);
		Booking savedResource = this.bookingService.createBooking(flightId, passengers, booking);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse<Booking>("success","Resource created successfully", savedResource)
		);
	}
	
	@GetMapping("/booking")
	public ResponseEntity< ApiResponse< List<Booking>  > > getAllBooking() {
		
		List<Booking> fetchedResource = this.bookingService.getAllBooking();
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse< List<Booking> >("success","Resource fetched successfull", fetchedResource)
		);
	}
	

	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<ApiResponse<Booking> > getBookingById(@PathVariable("bookingId") Integer bookingId) {
		
		Booking fetchedResource = this.bookingService.getBookingById(bookingId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Booking>("success","Resource fetched successfull", fetchedResource)
		);
	}
	
	@PutMapping("/booking/{bookingId}")
	public ResponseEntity<ApiResponse<Booking>> updateBooking(@PathVariable("bookingId") Integer bookingId, @RequestBody @Valid Booking booking) {
		
		Booking updatedResource = this.bookingService.updateBookingNonTransientFields(bookingId, booking);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<>("success", "Resource updated successfull", updatedResource)
				);
	}
	
	@PutMapping("/booking/{bookingId}/flight/{currentFlightId}/new/{newFlightId}")
	public ResponseEntity< ApiResponse<?> > updateFlightDetailsOfBooking(@PathVariable("bookingId") Integer bookingId, @PathVariable("currentFlightId") Integer currentFlightId, @PathVariable("newFlightId") Integer newFlightId){
		// call for service
		this.bookingService.updateFlightTransient(bookingId, currentFlightId, newFlightId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<>("success", "Flight details updated successfully", null)
				);
		
	}
	
	@PutMapping("/booking/{bookingId}/passenger/{currentPassengerId}/new/{newPassengerId}")
	public ResponseEntity< ApiResponse<?> > updatePassengerDetailsOfBooking(@PathVariable("bookingId") Integer bookingId, @PathVariable("currentPassengerId") Integer currentPassengerId, @PathVariable("newPassengerId") Integer newPassengerId){
		// call for service
		this.bookingService.updatePassengerTransient(bookingId, currentPassengerId, newPassengerId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<>("success", "Passenger details updated successfully", null)
				);
		
	}
	
	@DeleteMapping("/booking/{bookingId}/flight/{flightId}/passenger/{passengerId}")
	public ResponseEntity<ApiResponse<?>> deleteBooking(@PathVariable("bookingId") int bookingId, @PathVariable("flightId") int flightId, @PathVariable("passengerId") int passengerId) {
		
		//call for service
		this.bookingService.deleteBooking(bookingId, flightId, passengerId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Booking>("success", "Deleted Entry with Booking id " + bookingId, null)
				);		
	}
	
	// Get All Bookings By UserId
	
	@GetMapping("/booking/byUserId/{userId}")
	public ResponseEntity< ApiResponse<List<Booking> > > getBookingsByUserId(@PathVariable("userId") String userId){
		
		List<Booking> bookings = this.bookingService.getAllBookingsByUserId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<List<Booking>>("success", "Resource fetch successfull", bookings)
				);
	}
	
	// Mapping for get all Seats
	
	@GetMapping("/booking/getAllSeats/flight/{flightId}")
	public ResponseEntity< ApiResponse<List<Seat> > > getAllSeats(@PathVariable("flightId") Integer flightId){
		
		List<Seat> seatList = this.bookingService.getAllSeatsByFlightId(flightId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<List<Seat>>("success", "Resource fetch successfull", seatList)
				);
	}
	
	@PutMapping("/booking/updateSeat/{bookingId}")
	public ResponseEntity< ApiResponse<List<Seat> > > updateSeatsByBookingId(@PathVariable("bookingId") Integer bookingId, @RequestBody List<Seat> seatList){
		
		List<Seat> updatedSeatList = this.bookingService.updateSeatListByBookingId(bookingId, seatList);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<List<Seat>>("success", "Resource fetch successfull", updatedSeatList)
				);
	}
	
	@PutMapping("/booking/cancelBooking/{bookingId}")
	public ResponseEntity< ApiResponse<Booking > > cancelBooking(@PathVariable("bookingId") Integer bookingId){
		
		Booking booking = this.bookingService.cancelBooking(bookingId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Booking>("success", "Resource updated successfull", booking)
				);
	}
	
}
















