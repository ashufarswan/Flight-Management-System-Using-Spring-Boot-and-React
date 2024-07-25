package com.microservice.flights.service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.flights.exception.FlightAlreadyExistException;
import com.microservice.flights.exception.FlightNotFoundException;
import com.microservice.flights.models.Flight;
import com.microservice.flights.repo.FlightRepo;



@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	public FlightRepo repo;
	
	
//method to return all information
		@Override
		public List<Flight> getAllFlightDetails() {
			return repo.findAll();

			
		}
		
		
// method to add a flight
		@Override
		public Flight addFlight(Flight flight) {
			
			try {
				if(flight.getFlightId() != null) {
					this.getFlight(flight.getFlightId());
					throw new FlightAlreadyExistException(flight.getFlightId()+" Flight ID already exist");	
				}
				throw new FlightNotFoundException(null);
					
							
			}
			catch(FlightNotFoundException e) {
				
			}
			return repo.save(flight);
			
		}


//method to get information by id
		@Override
		public Flight getFlight(Integer flightId) {
			
			return repo.findById(flightId).orElseThrow(() ->  new FlightNotFoundException(flightId+" Flight ID does not exist"));
			
		}


//method to update information with flight object
		@Override
		public Flight updateFlight(Integer flightId, Flight flight) {
			
			Flight currentFlight = this.getFlight(flightId);
			
			
			if(flight.getDeparture() != null) 
				currentFlight.setDeparture(flight.getDeparture());
			
			if(flight.getDestination() != null)
				currentFlight.setDestination(flight.getDestination());
			
			if(flight.getDepartureDateAndTime() != null)
				currentFlight.setDepartureDateAndTime(flight.getDepartureDateAndTime());
			
			if(flight.getArrivalDateAndTime() != null)
				currentFlight.setArrivalDateAndTime(flight.getArrivalDateAndTime());
			
			if(flight.getDuration() != null)
				currentFlight.setDuration(flight.getDuration());
			
			if(flight.getAirline() != null)
				currentFlight.setAirline(flight.getAirline());
			
			if(flight.getPrice() != null)
				currentFlight.setPrice(flight.getPrice());
			
			if(flight.getAirlineType() != null)
				currentFlight.setAirlineType(flight.getAirlineType());
			
			
			return repo.save(currentFlight);
		}


//method to delete object by id 
		@Override
		public boolean deleteFlight(int flightId) {
			Flight cur = this.getFlight(flightId);
			if(cur.getBookingIdList() == null || cur.getBookingIdList().size()==0) {
				repo.deleteById(flightId);
				return true;
			}
			
			return false;
			
		}


		@Override
		public Flight getFlightByBookingId(Integer bookingId) {
			return this.repo.findByBookingId(bookingId);
			
		}


		@Override
		public Flight postBookingIdToFlight(Integer flightId, Integer bookingId) {
			Flight savedFlight = this.getFlight(flightId);
			
			if(savedFlight.getBookingIdList() == null) {
				savedFlight.setBookingIdList(Arrays.asList(bookingId));
			}
			else {
				savedFlight.getBookingIdList().add(bookingId);
			}
			return this.repo.save(savedFlight);
		}


		@Override
		public void deleteBookingIdFromFlight(Integer flightId, Integer bookingId) {
			Flight savedFlight = this.getFlight(flightId);
			
			if(savedFlight.getBookingIdList().isEmpty() == true)
				return;
			
			List<Integer> newBookingList = savedFlight.getBookingIdList().stream().filter(id -> id != bookingId).collect(Collectors.toList());
			savedFlight.setBookingIdList(newBookingList);
			this.repo.save(savedFlight);
			return ;
		}


		
	
}