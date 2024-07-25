package com.microservice.flights.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

import com.microservice.flights.models.ApiResponse;
import com.microservice.flights.models.Flight;
import com.microservice.flights.service.FlightService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class FlightController {

	@Autowired
	private FlightService service;
	
	  @GetMapping("/flight")
			public ResponseEntity< ApiResponse< List<Flight> > > getAllFlightDetails(
					@RequestParam(name="departure", required = false) String departure,
					@RequestParam(name="destination", required = false) String destination,
					@RequestParam(name="departureDateAndTime", required = false) String departureDateAndTime,
					@RequestParam(name="airline", required = false) String airline ,
					@RequestParam(name="priceMin", required = false) Double priceMin,
					@RequestParam(name="priceMax", required = false) Double priceMax
					) {
				
				List<Flight> flights = service.getAllFlightDetails();
				
				
				if(departure != null) {
					flights = flights.stream().filter( flight -> flight.getDeparture().equalsIgnoreCase(departure) ).collect(Collectors.toList());
				}
				
				if(destination != null) {
					flights = flights.stream().filter( flight -> flight.getDestination().equalsIgnoreCase(destination) ).collect(Collectors.toList());
				}
				
				if(departureDateAndTime != null) {
					 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					 LocalDate date = LocalDate.parse(departureDateAndTime, formatter);
								
						flights =flights.stream()
		                          .filter(flight -> flight.getDepartureDateAndTime().toLocalDate().equals(date))
		                          .collect(Collectors.toList());
				}
				
				if(airline != null) {
					flights = flights.stream().filter( flight -> flight.getAirline().equalsIgnoreCase(airline) ).collect(Collectors.toList());
				}
				
				if(priceMin != null) {
					flights = flights.stream().filter( flight -> flight.getPrice() >= priceMin).collect(Collectors.toList());
				}
				
				if(priceMax != null) {
					flights = flights.stream().filter( flight -> flight.getPrice() <= priceMax).collect(Collectors.toList());
				}
				
				
				
				return ResponseEntity.status(HttpStatus.OK).body(
						new ApiResponse< List<Flight> >("success","Resources fetched successfull", flights)
				);
				
			}
	
	@GetMapping("/flight/{flightId}")
	public ResponseEntity< ApiResponse<Flight> > getFlightById(@PathVariable("flightId") int flightId) {
		Flight fetchedResource =  service.getFlight(flightId);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Flight>("success","Resource fetched successfull", fetchedResource)
		);
	}
	
	
	@PostMapping("/flight")
	public ResponseEntity<ApiResponse<Flight> >  addFlight(@RequestBody @Valid Flight flight) {
		Flight savedResource = service.addFlight(flight);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse<Flight>("success","Resource created successfully", savedResource)
		);
	}
	
	
	
	@PutMapping("/flight/{flightId}")
	public ResponseEntity<ApiResponse<Flight> > updateFlight(@PathVariable("flightId") Integer flightId, @RequestBody @Valid Flight flight) {
		Flight updatedResource = service.updateFlight(flightId ,flight);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<>("success", "Resource updated successfull", updatedResource)
				);
		
	}
	
	
	@DeleteMapping("/flight/{flightId}")
	public ResponseEntity<ApiResponse<?>> deleteFlight(@PathVariable("flightId") int flightId)
	{
		if(service.deleteFlight(flightId)) {
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse<Flight>("success", "Deleted Entry with flight id " + flightId, null)
				);	}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				new ApiResponse<Flight>("conflict", "Can;t Delete Entry with flight id " + flightId, null));
		
	}
	
	
	// Mapping for Storing Booking Id to the Flight Objects
	
	@GetMapping("/flight/booking/{bookingId}")
	public Flight getFlightByBookingId(@PathVariable("bookingId") Integer bookingId ) {
		return this.service.getFlightByBookingId(bookingId);
	}
	
	@PostMapping("flight/{flightId}/booking/{bookingId}")
	public Flight postBookingIdToFlightService(@PathVariable("flightId") Integer flightId, @PathVariable("bookingId") Integer bookingId) {
		return this.service.postBookingIdToFlight(flightId, bookingId);
	}
	
	@DeleteMapping("flight/{flightId}/booking/{bookingId}")
	public void deleteBookingIdFromFlight(@PathVariable("flightId") Integer flightId, @PathVariable("bookingId") Integer bookingId) {
		this.service.deleteBookingIdFromFlight(flightId, bookingId);
	}
	
	



}
