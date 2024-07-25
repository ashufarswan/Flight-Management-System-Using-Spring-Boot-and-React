package com.flight.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flight.booking.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByUserId(String userId);
}
