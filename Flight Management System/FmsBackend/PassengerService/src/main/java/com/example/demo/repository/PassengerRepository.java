package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Passenger;


@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
	 @Query("SELECT p FROM Passenger p WHERE :bookingId IN (SELECT b FROM p.bookingIdList b)")
	    List<Passenger> findByBookingId(@Param("bookingId") Integer bookingId);
}
