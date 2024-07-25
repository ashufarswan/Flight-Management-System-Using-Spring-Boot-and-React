package com.flight.booking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flight.booking.exception.BookingNotFoundException;
import com.flight.booking.models.Booking;
import com.flight.booking.models.Flight;
import com.flight.booking.models.Passenger;
import com.flight.booking.models.Seat;
import com.flight.booking.repository.BookingRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceImpTest {
	
	@Mock
	private BookingRepository bookingRepository;
	
	@Mock
	private FlightClient flightClient;
	
	@Mock
	private PassengerClient passengerClient;
	
	@InjectMocks
	private BookingServiceImp bookingService;
	
	@Test
	void testGetAllBookings() {
				
		when(bookingRepository.findAll()).thenReturn(this.getDummyBookingList());
		
		when( flightClient.getFlightByBookingId( any(Integer.class) ) ).thenReturn(this.getDummyFlightObj());
		
		when( passengerClient.getPassengersByBookingId( any(Integer.class)  ) ).thenReturn(this.getDummyPassengerList());
		
		List<Booking> bookings = bookingService.getAllBooking();
		
		assertEquals(this.getDummyBookingList().size(), bookings.size());
		
		// checks booking Flight is not null
		bookings.forEach(booking -> assertNotNull(booking.getFlight()));
		
		// checks booking PassengerList is not Null
		bookings.forEach(booking -> assertNotNull(booking.getPassengerList()));
	}
	
	@Test
	void testGetAllBookingsForEmptyList() {
		
		when(bookingRepository.findAll()).thenReturn(getEmptyDummyBookingList());
		
		List<Booking> bookings = bookingService.getAllBooking();
		
		assertEquals(0, bookings.size());
		
	}
	
	@Test
	void testGetBookingByIdIfBookingIdPresent() {
		Optional<Booking> dummyOptionalObj = Optional.of(this.getDummyBookingList().get(0));
		
		when(bookingRepository.findById(1)).thenReturn(dummyOptionalObj);
		
		when( flightClient.getFlightByBookingId( any(Integer.class) ) ).thenReturn(this.getDummyFlightObj());
		
		when( passengerClient.getPassengersByBookingId( any(Integer.class)  ) ).thenReturn(this.getDummyPassengerList());
		
		
		Booking actualBookingObj = assertDoesNotThrow(() -> bookingService.getBookingById(1));
		
		assertNotNull(actualBookingObj.getFlight());
		assertNotNull(actualBookingObj.getPassengerList());
		
	}
	
	@Test
	void testGetBookingIdIfBookingIdNotPresent() {
		Optional<Booking> emptyOptionalObj = Optional.empty();
		when(bookingRepository.findById(9999)).thenReturn(emptyOptionalObj);
		
		BookingNotFoundException ex = assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(9999));
		assertEquals("Booking with id: 9999 not found", ex.getMessage());
	}
	
	
	@Test
	void testCreateBooking() {
		when(flightClient.postBookingIdToFlightService( any(Integer.class), any(Integer.class) ) ).thenReturn(getDummyFlightObj());
		when(passengerClient.postBookingIdToPassengerService( any(Integer.class), any(Integer.class) ) ).thenReturn(getDummyPassengerList().get(0));
		when(bookingRepository.save( any(Booking.class) )).thenReturn(this.getDummyBookingList().get(0));
		
		assertNotNull( bookingService.createBooking(1, Arrays.asList("2"), this.getDummyBookingList().get(0) ));
	}
	
	@Test
	void updateBookingNonTransientFieldsTestIfBookingIdIsPresent(){
		
		Optional<Booking> dummyOptionalObj = Optional.of(this.getDummyBookingList().get(1));
		
		when(bookingRepository.findById(1)).thenReturn(dummyOptionalObj);
		
		when( flightClient.getFlightByBookingId( any(Integer.class) ) ).thenReturn(this.getDummyFlightObj());
		
		when( passengerClient.getPassengersByBookingId( any(Integer.class)  ) ).thenReturn(this.getDummyPassengerList());
		
		when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		
		Integer updatedNumberOfPassengers = 2;
		
		List<Seat> updatedSeatList = Arrays.asList(new Seat(4, 55, 3, "Economy", true, "Middle", null),
				new Seat(3, 55, 4, "Business", true, "Window", null));
		
		Date updatedDate = new Date();
		
		String updatedStatus = "paid";
		
		Booking updatedBookingObj = 
				new Booking(2, null, updatedNumberOfPassengers, null, "1234",updatedDate, updatedSeatList, updatedStatus);

		Booking actualUpdatedBookingObj = assertDoesNotThrow(() -> 
		bookingService.updateBookingNonTransientFields(1, updatedBookingObj )
		);
		
		// verifies numberOfPassengers are updated correctly
		assertEquals(updatedNumberOfPassengers, actualUpdatedBookingObj.getNumberOfPassengers());
		
		// verifies BookingDateAndTime are updated correctly
		assertEquals(updatedDate, actualUpdatedBookingObj.getBookingDateAndTime());
		
		// verifies SeatList are updated correctly
		assertIterableEquals(updatedSeatList, actualUpdatedBookingObj.getSeatList());
		
		// verifies BookingStatus are updated correctly
		assertEquals(updatedStatus, actualUpdatedBookingObj.getBookingStatus());	
	}
	
	@Test
	void updateBookingNonTransientFieldsTestIfBookingIdIsPresentUpdateOnlySeatList(){
		
		Optional<Booking> dummyOptionalObj = Optional.of(this.getDummyBookingList().get(1));
		
		when(bookingRepository.findById(1)).thenReturn(dummyOptionalObj);
		
		when( flightClient.getFlightByBookingId( any(Integer.class) ) ).thenReturn(this.getDummyFlightObj());
		
		when( passengerClient.getPassengersByBookingId( any(Integer.class)  ) ).thenReturn(this.getDummyPassengerList());
		
		when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
	
		List<Seat> updatedSeatList = Arrays.asList(new Seat(4, 55, 3, "Economy", true, "Middle", null),
				new Seat(3, 55, 4, "Business", true, "Window", null));
		
		
		Booking updatedBookingObj = 
				new Booking(2, null, null, null, null,null, updatedSeatList, null);

		Booking actualUpdatedBookingObj = assertDoesNotThrow(() -> 
		bookingService.updateBookingNonTransientFields(1, updatedBookingObj )
		);
		
		// verifies SeatList are updated correctly
		assertIterableEquals(updatedSeatList, actualUpdatedBookingObj.getSeatList());
		
	}
	
	@Test
	void updateBookingNonTransientFieldsTestIfBookingIdIFNotPresent(){
		Optional<Booking> emptyOptionalObj = Optional.empty();
		
		Integer bookingIdToSearch = 9999;
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(emptyOptionalObj);
		
		BookingNotFoundException ex = assertThrows(BookingNotFoundException.class, () -> bookingService.updateBookingNonTransientFields( bookingIdToSearch, getDummyBookingList().get(0) ) );
		
		assertEquals( String.format("Booking with id: %d not found", bookingIdToSearch), ex.getMessage());
		
	}
	
	@Test
	void updateFlightTransientIfBookingIdIsPresent() {
		Optional<Booking> dummyOptionalObj = Optional.of(this.getDummyBookingList().get(0));
		Integer bookingIdToSearch = getDummyBookingList().get(0).getBookingId();
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(dummyOptionalObj);
		
		doNothing().when( flightClient).deleteBookingIdFromFlight( any(Integer.class), any(Integer.class) ) ;
		
		when( flightClient.postBookingIdToFlightService( any(Integer.class), any(Integer.class) ) ).thenReturn(this.getDummyFlightObj());
		
		bookingService.updateFlightTransient(bookingIdToSearch, 2, 3);
		
		verify(flightClient).deleteBookingIdFromFlight(2, bookingIdToSearch);
		verify(flightClient).postBookingIdToFlightService(3, bookingIdToSearch);
	}
	
	@Test
	void updateBookingFlightTransientFieldsIfBookingIdNotPresent(){
		Optional<Booking> emptyOptionalObj = Optional.empty();
		
		Integer bookingIdToSearch = 9999;
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(emptyOptionalObj);
		
		BookingNotFoundException ex = assertThrows(BookingNotFoundException.class, () -> bookingService.updateFlightTransient(bookingIdToSearch, 1, 2) );
		
		assertEquals( String.format("Booking with id: %d not found", bookingIdToSearch), ex.getMessage());
		
	}
	
	
	@Test
	void updatePassengerTransientIfBookingIdIsPresent() {
		Optional<Booking> dummyOptionalObj = Optional.of(this.getDummyBookingList().get(0));
		Integer bookingIdToSearch = getDummyBookingList().get(0).getBookingId();
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(dummyOptionalObj);
		
		doNothing().when(passengerClient).deleteBookingIdFromPassenger( any(Integer.class), any(Integer.class) ) ;
		
		when( passengerClient.postBookingIdToPassengerService( any(Integer.class), any(Integer.class) ) ).thenReturn(this.getDummyPassengerList().get(0));
		
		bookingService.updatePassengerTransient(bookingIdToSearch, 1, 2);
		
		verify(passengerClient).deleteBookingIdFromPassenger(1, bookingIdToSearch);
		verify(passengerClient).postBookingIdToPassengerService(2, bookingIdToSearch);
		
		
	}
	
	@Test
	void updateBookingPassengerTransientFieldsIfBookingIdNotPresent(){
		Optional<Booking> emptyOptionalObj = Optional.empty();
		
		Integer bookingIdToSearch = 9999;
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(emptyOptionalObj);
		
		BookingNotFoundException ex = assertThrows(BookingNotFoundException.class, () -> bookingService.updatePassengerTransient(bookingIdToSearch, 1, 2) );
		
		assertEquals( String.format("Booking with id: %d not found", bookingIdToSearch), ex.getMessage());
		
	}
	
	@Test
	void deleteBookingIfBookingIdPresent() {
		Optional<Booking> dummyOptionalObj = Optional.of(this.getDummyBookingList().get(0));
		Integer bookingIdToSearch = getDummyBookingList().get(0).getBookingId();
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(dummyOptionalObj);
		
		doNothing().when(flightClient).deleteBookingIdFromFlight( any(Integer.class), any(Integer.class) ) ;
		doNothing().when(passengerClient).deleteBookingIdFromPassenger( any(Integer.class), any(Integer.class) ) ;
		
		bookingService.deleteBooking(bookingIdToSearch, 1, 1);
		verify(flightClient).deleteBookingIdFromFlight( 1, 1);
		
		verify(passengerClient).deleteBookingIdFromPassenger(1, 1);
		
	}
	
	@Test
	void deleteBookingIfBookingIdNotPresent() {
		Optional<Booking> emptyOptionalObj = Optional.empty();
		
		Integer bookingIdToSearch = 9999;
		
		when(bookingRepository.findById(bookingIdToSearch)).thenReturn(emptyOptionalObj);
		
		BookingNotFoundException ex = assertThrows(BookingNotFoundException.class, () -> bookingService.deleteBooking(bookingIdToSearch, 1, 2) );
		
		assertEquals( String.format("Booking with id: %d not found", bookingIdToSearch), ex.getMessage());
		
	}
	
	
	
	List<Booking> getEmptyDummyBookingList(){
		return new ArrayList<Booking>();
	}
	
	Flight getDummyFlightObj() {
		 return new Flight(1, "Delhi", "Dehradun", null, null, 60, "Air India", "Domestic", 5000,
					null);
	}
	
	List<Passenger> getDummyPassengerList(){
		 return Arrays.asList(
					new Passenger(1, "Dummy1", "lastName", "email@cc.com", "7891234560", null, null, "MP22GEY1", "Male", "Window", null),
					new Passenger(1, "Dummy2", "Name", "email@cc.com", "8971234560", null, null, "MP22GEY1", "Male", "Window", null)
					);
	}
	
	List<Booking> getDummyBookingList() {

		// Creating List<Seat> with dummy Values
		List<Seat> dummySeatList1 = Arrays.asList(new Seat(1, 22, 1, "Economy", true, "Window", null),
				new Seat(2, 44, 2, "Business", true, "Window", null));

		List<Seat> dummySeatList2 = Arrays.asList(new Seat(2, 33, 3, "Economy", true, "Middle", null),
				new Seat(3, 55, 4, "Business", true, "Middle", null));

		
		// Creating List<Booking> with dummy values
		return Arrays.asList(new Booking(1, null, 1, null,"12345", new Date(), dummySeatList1, "created"),
				new Booking(2, null, 1, null, "12346", new Date(), dummySeatList2, "created"));
	}
}
