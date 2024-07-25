package com.fare.service;

import java.util.List;

import com.fare.exception.InvalidIdException;
import com.fare.model.ETicket;

public interface EticketService {
	public List<ETicket> createEticket(int bookingid) throws InvalidIdException, Exception;
	public ETicket getEticket(String ticketId) throws InvalidIdException;
	public List<ETicket> getEticketsByBookingId(int bookingId) throws InvalidIdException;
	public List<ETicket> getAllEticket();
}
