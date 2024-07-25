package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Passenger;

public interface PassengerService {
	
	public Passenger savePassenger(Passenger passsenger);
	public Passenger fetchPassengerById(int passengerId);
//	public List<Passenger> fetchPassengerListByFlightId(int flightId);
	public Passenger updatePassenger(int passengerId, Passenger passenger);
	public void deletePassenger(int passengerId);
	public List<Passenger> fetchAllPassengers();
	
	public List<Passenger> fetchByBookingId(Integer bookingId);
	public void deleteBookingId(Integer passengerId,Integer bookingId);
	public Passenger addBookingId(Integer passengerId,Integer bookingId);

}
