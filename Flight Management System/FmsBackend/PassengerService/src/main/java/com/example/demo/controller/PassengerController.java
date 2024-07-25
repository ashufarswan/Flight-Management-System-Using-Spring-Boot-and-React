package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ApiResponse;
import com.example.demo.models.Passenger;
import com.example.demo.service.PassengerService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api")
public class PassengerController {
	
	@Autowired
	private PassengerService passengerService;
	
							// adding passenger in database
	
	@PostMapping("/passenger")
	public ResponseEntity<ApiResponse<Passenger>> savePassengerToDB(@RequestBody @Valid Passenger passsenger) {
		
		Passenger savedPassenger =  this.passengerService.savePassenger(passsenger);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse<>("success", "New passenger created successfully", savedPassenger)
				);
				
	}
	
	// getting all pasangers
	@GetMapping("/passenger")
	public ResponseEntity< ApiResponse< List<Passenger> > > getAllPassenger() {
			
			List<Passenger> data = null;
			
			
			data = this.passengerService.fetchAllPassengers();
			
			return ResponseEntity.status(HttpStatus.OK).body(
					new ApiResponse< List<Passenger> >("success","All Passengers fetched successfully", data)
					);
	}
	
	
	
	
	
	
	
	
							// getting passenger by passenger id
	
	@GetMapping("/passenger/{passengerId}")
	public ResponseEntity<ApiResponse<Passenger>> getNewsById(@PathVariable int passengerId) {
		
		Passenger data = this.passengerService.fetchPassengerById(passengerId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Passenger>("success", "Passenger fetched successfully", data)
				);
	}
	
							// getting list og passengers by flight id
	
//	@GetMapping("/flight/{flightId}")
//	public ResponseEntity<ApiResponse<List<Passenger>>> getPassengerListByFlightId(@PathVariable int flightId) {
//			
//			List<Passenger> data = this.passengerService.fetchPassengerListByFlightId(flightId);
//			
//			return ResponseEntity.status(HttpStatus.OK).body(
//					new ApiResponse<List<Passenger>>("success", "List of passengers fetched successfully", data)
//					);
//	}
//	
	
	
	@PutMapping("/passenger/{passengerId}")
	public  ResponseEntity<ApiResponse<Passenger>> updatePassenger(@PathVariable int passengerId, @RequestBody @Valid Passenger passenger) {
			
			Passenger updatedPassenger = this.passengerService.updatePassenger(passengerId, passenger);
			
			return ResponseEntity.status(HttpStatus.OK).body(
					new ApiResponse<>("success", "Passenger details updated successfully.", updatedPassenger)
					);
	}
	
									// deleting passenger by passenger Id
	
	@DeleteMapping("/passenger/{passengerId}")
	public ResponseEntity<ApiResponse<?>> deletePassenger(@PathVariable("passengerId") int passengerId) {
		
		this.passengerService.deletePassenger(passengerId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Passenger>("success", "Deleted Passenger with passenger id " + passengerId, null)
				);		
	}
		
									// getting list of passengers by bookingId
	
	@GetMapping("/passenger/booking/{bookingId}")
	public  List<Passenger> getPassengersByBookingId(@PathVariable("bookingId") Integer bookingId ){
		
	List<Passenger> data = this.passengerService.fetchByBookingId(bookingId);
			
			return data;
	}
	
	
									// Deleting booking id from BookingId list of a passenger
	
	@DeleteMapping("/passenger/{passengerId}/booking/{bookingId}")
	public void deleteBookingIdFromPassenger(@PathVariable("passengerId") Integer passengerId, @PathVariable("bookingId") Integer bookingId) {
		
		this.passengerService.deleteBookingId(passengerId,bookingId);
		
		
		
	}
	
									// adding booking id in booking id list
	@PostMapping("/passenger/{passengerId}/booking/{bookingId}")
	public Passenger postBookingIdToPassengerService(@PathVariable("passengerId") Integer passengerId ,@PathVariable("bookingId") Integer bookingId) {
		
		Passenger p=this.passengerService.addBookingId(passengerId,bookingId);
		return p;
	}

}
