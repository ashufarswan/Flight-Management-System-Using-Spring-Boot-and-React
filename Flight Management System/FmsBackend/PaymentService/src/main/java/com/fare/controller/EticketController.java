package com.fare.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.fare.exception.InvalidIdException;
import com.fare.model.ETicket;
import com.fare.service.EticketService;
 
@RestController
@RequestMapping("/api/")
public class EticketController {
	@Autowired
	EticketService fareService;

	@GetMapping("/eticket/createEticket/{bookingId}")
	public ResponseEntity<List<ETicket>> createEticket(@PathVariable("bookingId") int bookingId) throws InvalidIdException,Exception {
		List<ETicket> eTicketList = fareService.createEticket(bookingId);
	    return new ResponseEntity<>(eTicketList,HttpStatus.OK);
	}

	@GetMapping("/eticket/{ticketId}")
	public ResponseEntity<ETicket> getTicket(@PathVariable("ticketId") String ticketId) throws InvalidIdException {
		return new ResponseEntity<>(fareService.getEticket(ticketId),HttpStatus.OK);
	}
	@GetMapping("/eticket")
	public ResponseEntity<List<ETicket>> getAllTicket() {
		return new ResponseEntity<>(fareService.getAllEticket(),HttpStatus.OK);
	}
	@GetMapping("/eticket/bookingId/{bookingId}")
	public ResponseEntity<List<ETicket>> getTicketByBokingID(@PathVariable("bookingId") int bookingId) throws InvalidIdException {
		return new ResponseEntity<>(fareService.getEticketsByBookingId(bookingId),HttpStatus.OK);
	}
}