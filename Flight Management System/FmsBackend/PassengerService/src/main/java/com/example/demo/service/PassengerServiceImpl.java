package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.Passenger;
import com.example.demo.repository.PassengerRepository;



@Service
public class PassengerServiceImpl implements PassengerService {
	
	
	@Autowired
	private PassengerRepository passengerRepository;
	
	@Override
	public Passenger savePassenger(Passenger passsenger) {
		// TODO Auto-generated method stub
		
//		passenger = PassengerUtil.validatePassengerDetails(passenger);  
		
		// search if the record exists or not
		Passenger savedpassenger = null;
		try{
			
			// will throw duplicate Exception if already present
			if(passsenger.getPassengerId() != null) {
				this.fetchPassengerById(passsenger.getPassengerId());
				throw new DuplicateResourceException("Passenger with Id " + passsenger.getPassengerId() + " already exists");
			}
			throw new ResourceNotFoundException(null);
		} catch(ResourceNotFoundException e) {
			// resource does not exists
			// If not save record to DB and return the record
			savedpassenger = this.passengerRepository.save(passsenger);
		}
	
		
		return savedpassenger;
		
	}
	
	
	@Override
	public Passenger fetchPassengerById(int passengerId) {
		// TODO Auto-generated method stub
		Optional<Passenger> result = this.passengerRepository.findById(passengerId);
		
		// If not found throw ResourceNotFoundException
		Passenger pass  = result.orElseThrow( () -> 
				new ResourceNotFoundException("Passenger not Found with id " + passengerId) );
		
		return pass;
		
	}
	
//	@Override
//	public List<Passenger> fetchPassengerListByFlightId(int flightId) {
//		// TODO Auto-generated method stub
//		
//		
//		return null;
//	}
	
	@Override
	public Passenger updatePassenger(int passengerId, Passenger passenger) {
		// TODO Auto-generated method stub
		
		Passenger p = passengerRepository.findById(passengerId).orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + passengerId));
        
        // Update properties
		p.setFirstName(passenger.getFirstName());
		p.setLastName(passenger.getLastName());
		p.setEmailId(passenger.getEmailId());
		p.setPhoneNumber(passenger.getPhoneNumber());
		p.setDateOfBirth(passenger.getDateOfBirth());
		p.setNationality(passenger.getNationality());
		p.setGender(passenger.getGender());
		p.setPassportIdNumber(passenger.getPassportIdNumber());
		p.setSeatPreference(passenger.getSeatPreference());
		
        

        // Save the updated entity
        return passengerRepository.save(p);
		
		
	}

	@Override
	public void deletePassenger(int passengerId) {
		// TODO Auto-generated method stub
		passengerRepository.findById(passengerId).orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + passengerId));
		
		this.passengerRepository.deleteById(passengerId);
		
	}


	public List<Passenger> fetchAllPassengers() {
		// TODO Auto-generated method stub
		
		List<Passenger> ls = passengerRepository.findAll();
		if(ls.isEmpty()) {
			throw new ResourceNotFoundException("No Passengers are found");
		}
		
		return ls;
	}


	public List<Passenger> fetchByBookingId(Integer bookingId) {
		// TODO Auto-generated method stub
		
		List<Passenger> ls = passengerRepository.findByBookingId(bookingId);
		
		return ls;
	}


	@Override
	public void deleteBookingId(Integer passengerId, Integer bookingId) {
		// TODO Auto-generated method stub
		Passenger p = passengerRepository.findById(passengerId).orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + passengerId));
		
		if(p.getBookingIdList().isEmpty()) {
			return ;
		}
		
		List<Integer> newBookingList = p.getBookingIdList().stream().filter(id -> id != bookingId).collect(Collectors.toList());
		p.setBookingIdList(newBookingList);
		this.passengerRepository.save(p);
		return ;
		
	}


	public Passenger addBookingId(Integer passengerId, Integer bookingId) {
		// TODO Auto-generated method stub
		Passenger p = passengerRepository.findById(passengerId).orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + passengerId));
		
		p.getBookingIdList().add(bookingId);
		
		this.passengerRepository.save(p);
		
		return p;
	}
	
}
