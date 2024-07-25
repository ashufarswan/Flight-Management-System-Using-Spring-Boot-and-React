package com.microservice.flights.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservice.flights.models.Flight;

public interface FlightRepo extends JpaRepository<Flight, Integer> {
	@Query("SELECT f FROM Flight f WHERE :bookingId MEMBER OF f.bookingIdList")
    Flight findByBookingId(@Param("bookingId") Integer bookingId);
}
