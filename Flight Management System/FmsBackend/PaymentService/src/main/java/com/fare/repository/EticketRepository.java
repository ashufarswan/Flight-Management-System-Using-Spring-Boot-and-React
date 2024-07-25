package com.fare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fare.model.ETicket;

@Repository
public interface EticketRepository extends JpaRepository<ETicket,String> {

	List<ETicket> findAllByBookingID(int bookingId);

}
