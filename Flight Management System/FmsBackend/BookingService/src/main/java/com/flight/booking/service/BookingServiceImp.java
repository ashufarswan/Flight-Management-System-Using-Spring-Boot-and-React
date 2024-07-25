package com.flight.booking.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight.booking.exception.BookingNotFoundException;
import com.flight.booking.exception.DuplicateBookingException;
import com.flight.booking.models.Booking;
import com.flight.booking.models.Seat;
import com.flight.booking.repository.BookingRepository;

@Service
public class BookingServiceImp implements BookingService {
	
	
	private BookingRepository bookingRepository;
	
	
	private FlightClient flightClient;
	
	
	private PassengerClient passengerClient;
	
	
	 @Autowired
	    public BookingServiceImp(BookingRepository bookingRepository, 
	                             FlightClient flightClient,
	                             PassengerClient passengerClient) {
	        this.bookingRepository = bookingRepository;
	        this.flightClient = flightClient;
	        this.passengerClient = passengerClient;
	    }
	
	public List<Booking> getAllBooking() {
		List<Booking> fetchedBookings =  this.bookingRepository.findAll();
		
		// set Transient Objects to fetchedBookings List
		
		fetchedBookings = fetchedBookings.stream().map(bookingObj ->{
			
			// setting Flight Object using flightClient
			bookingObj.setFlight(flightClient.getFlightByBookingId(bookingObj.getBookingId()));
			
			// setting Passengers List from passengerClient
			bookingObj.setPassengerList( passengerClient.getPassengersByBookingId(bookingObj.getBookingId()) );
			
			return bookingObj;
		}).collect(Collectors.toList());
		
		return fetchedBookings;
	}
	
	public Booking getBookingById(Integer bookingId){
		Optional<Booking> opt = this.bookingRepository.findById(bookingId);
		
		Booking fetchedBooking = opt.orElseThrow(() ->  new BookingNotFoundException( String.format("Booking with id: %d not found", bookingId) ) );
		
		// get flight from FlightClient
		fetchedBooking.setFlight(flightClient.getFlightByBookingId(bookingId));
		
		// get List of Passengers from PassengerClient
		fetchedBooking.setPassengerList(passengerClient.getPassengersByBookingId(bookingId));
		
		return fetchedBooking;
	}
	
	// check if already exists or not
	public Booking createBooking(Integer flightId, List<String> passengers, Booking bookingToCreate) {
		
		// this will throw error if bookingId is not present in the booking object
//		try{
//			this.getBookingById(bookingToCreate.getBookingId());
//			throw new DuplicateBookingException(  String.format("Booking with id: %d already exists", bookingToCreate.getBookingId()) );
//			
//		} catch (BookingNotFoundException e) {
//			
//		}
		
		Integer booking_id = genrateUniqueRandomId();
		bookingToCreate.setBookingId(booking_id);
		
		// save booking Id to Flight service using FlightClient
		flightClient.postBookingIdToFlightService(flightId, booking_id);
		
		
		// save booking Id to all passengers in Passenger service using PassengerClient
		for(String passengerId : passengers) {
			passengerClient.postBookingIdToPassengerService(Integer.valueOf(passengerId), booking_id);			
		}
		
		
		Booking savedBookingObj = bookingRepository.save(bookingToCreate);
		
		// setting flight object to saved booking Object
		savedBookingObj.setFlight(flightClient.getFlightByBookingId( booking_id ));
		
		// setting Passenger list to saved Booking Object
		savedBookingObj.setPassengerList(passengerClient.getPassengersByBookingId( booking_id ));
		
		return savedBookingObj;
	}
	
	
	public Booking updateBookingNonTransientFields(Integer bookingId, Booking updatedBookingObj) {
		
		Booking savedBookingObj = this.getBookingById(bookingId);
		
		// updating any non transient values which are not Null
		
		if(updatedBookingObj.getNumberOfPassengers() != null )
			savedBookingObj.setNumberOfPassengers(updatedBookingObj.getNumberOfPassengers());
		
		if(updatedBookingObj.getBookingDateAndTime() != null) {
			savedBookingObj.setBookingDateAndTime(updatedBookingObj.getBookingDateAndTime());
		}
		
		if(updatedBookingObj.getSeatList() != null) {
			savedBookingObj.updateSeatList(updatedBookingObj.getSeatList());
		}
		if(updatedBookingObj.getBookingStatus() != null) {
			savedBookingObj.setBookingStatus(updatedBookingObj.getBookingStatus());
		}
		
		// saving updatedBookingObj to db
		Booking updatedBooking = bookingRepository.save(savedBookingObj);
		
		// setting flight from FlightClient
		updatedBooking.setFlight(flightClient.getFlightByBookingId( updatedBooking.getBookingId() ));
				
		// setting List of Passengers from PassengerClient
		updatedBooking.setPassengerList(passengerClient.getPassengersByBookingId( updatedBooking.getBookingId() ));

		
		return updatedBooking;
	}
	
	// Non Transient Fields
	public void updateFlightTransient(Integer bookingId, Integer currentFlightId, Integer newFlightId) {
		
		// this verifies that booking is present for given bookingId
		this.getBookingById(bookingId);
		
		// remove BookingId from currentFlightId from Flight Service
		this.flightClient.deleteBookingIdFromFlight(currentFlightId, bookingId);
		
		// add newFlightId to BookingId in Flight Service
		this.flightClient.postBookingIdToFlightService(newFlightId, bookingId);
	}
	
	// Non Transient Fields
	public void updatePassengerTransient(Integer bookingId, Integer currentPassengerId, Integer newPassengerId) {
		
		// this verifies that booking is present for given bookingId
		this.getBookingById(bookingId);
				
		// remove BookingId from currentPassengerId from Passenger Service
		this.passengerClient.deleteBookingIdFromPassenger(currentPassengerId, bookingId);
				
		// add newPassengerId to BookingId in Passenger Service
		this.passengerClient.postBookingIdToPassengerService(newPassengerId, bookingId);
	}
	
	public void deleteBooking(Integer bookingId, Integer flightId, Integer passengerId) {
		Booking booking = this.getBookingById(bookingId);
		
		// delete associated booking from flight service
		this.flightClient.deleteBookingIdFromFlight(flightId, bookingId);
		
		// delete associated booking from Passenger service
		this.passengerClient.deleteBookingIdFromPassenger(passengerId, bookingId);
		
		this.bookingRepository.delete(booking);
		return ;
	}
	
	public Integer  genrateUniqueRandomId() {
		Random random = new Random();
	    Integer id = random.nextInt(999999)+1;
		
		Optional<Booking> booking = bookingRepository.findById(id);
		if(booking.isPresent()) {
			id = genrateUniqueRandomId();
		}
		
		return id;
	}

	
	@Override
	public List<Booking> getAllBookingsByUserId(String userId) {
		List<Booking> bookings  = this.bookingRepository.findByUserId(userId);
		
		return bookings.stream().map(booking ->{
			booking.setFlight( this.flightClient.getFlightByBookingId(booking.getBookingId()) );
			booking.setPassengerList(this.passengerClient.getPassengersByBookingId(booking.getBookingId()));
			return booking;
		}).collect(Collectors.toList());
	}
	
	
	
	@Override
	public List<Seat> getAllSeatsByFlightId(Integer flightId) {
		
		List<Booking> filteredBooking = this.getAllBooking().stream().filter(
				booking -> booking.getFlight().getFlightId() == flightId
				).collect(Collectors.toList());
		
		Set<Seat> seatSet = new HashSet<>();
		
		filteredBooking.forEach(booking -> seatSet.addAll(booking.getSeatList()) );
		
		return new ArrayList<>(seatSet);
	}

	@Override
	public List<Seat> updateSeatListByBookingId(Integer bookingId, List<Seat> seatList) {
		Booking booking = this.getBookingById(bookingId);
		
		booking.updateSeatList(seatList);
		
		Booking savedBooking = this.bookingRepository.save(booking);
		
		return savedBooking.getSeatList();
	}
	
	
	@Override
	public Booking cancelBooking(Integer bookingId) {
		Booking booking = this.getBookingById(bookingId);
		if(booking.getBookingStatus().equals("created")) {
			booking.setBookingStatus("cancelled");
		}
		else if(booking.getBookingStatus().equals("paid")){
			booking.updateSeatList(new ArrayList<>());
			booking.setBookingStatus("refunded");
			
		}
		return bookingRepository.save(booking);
		
	}

	
}
