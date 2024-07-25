package com.flight.booking.service;

import java.util.List;

import com.flight.booking.models.Booking;
import com.flight.booking.models.Seat;

public interface BookingService {
	List<Booking> getAllBooking();
	Booking getBookingById(Integer bookingId);
	Booking createBooking(Integer flightId, List<String> passengers, Booking bookingToCreate);
	Booking updateBookingNonTransientFields(Integer bookingId, Booking updatedBookingObj);
	void updateFlightTransient(Integer bookingId, Integer currentFlightId, Integer newFlightId);
	void updatePassengerTransient(Integer bookingId, Integer currentPassengerId, Integer newPassengerId);
	void deleteBooking(Integer bookingId, Integer flightId, Integer passengerId);
	
	// Fetch Bookings by UserId
	List<Booking> getAllBookingsByUserId(String userId);
	
	// CRUD operations for Seat by Booking Id or flightId
	List<Seat> getAllSeatsByFlightId(Integer flightId); 
	List<Seat> updateSeatListByBookingId(Integer bookingId, List<Seat> seatList);
	Booking cancelBooking(Integer bookingId);
	
}
