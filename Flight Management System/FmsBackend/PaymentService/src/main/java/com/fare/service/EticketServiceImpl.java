package com.fare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fare.exception.InvalidIdException;
import com.fare.model.ApiResponse;
import com.fare.model.Booking;
import com.fare.model.ETicket;
import com.fare.model.Passenger;
import com.fare.model.PaymentReceipt;
import com.fare.model.Seat;
import com.fare.repository.EticketRepository;
import com.fare.repository.PaymentReceiptRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class EticketServiceImpl implements EticketService{
	
	Logger logger = LoggerFactory.getLogger(EticketServiceImpl.class);
	
	@Autowired
	private EticketRepository fareRepository;
	
	@Autowired
	private BookingClient bookingClient;

	@Autowired
	private PaymentReceiptRepository paymentReceiptRepository;

	@Override
	public List<ETicket> createEticket(int bookingid) throws Exception {
		
			
			ResponseEntity<ApiResponse<Booking>> booking = bookingClient.getBookingById(bookingid);
			
			//Check for Invalid Booking Id
			if(booking.getStatusCode().value() == HttpStatus.NOT_FOUND.value()  ) {
				throw new InvalidIdException("Invalid booking id "+bookingid);
			}
			Optional<List<PaymentReceipt>> paymentReceiptLsit = paymentReceiptRepository.findByBookingId(bookingid);
			
			PaymentReceipt paymentReceipt = new PaymentReceipt();
			for(PaymentReceipt p : paymentReceiptLsit.get()) {
				if(p.getPaymentStaus().equals("paid")) {
					paymentReceipt =p;
				}
			}
			
			if(paymentReceiptLsit.get() == null) {
				throw new InvalidIdException("No order created with this id");
			}
			
			List<Passenger> passengerList = booking.getBody().getData().getPassengerList();
			List<Seat> seatList = booking.getBody().getData().getSeatList();
			int i = 0;
			List<ETicket> eTicketList = new ArrayList<>();
			if(paymentReceipt.getPaymentStaus() != null && paymentReceipt.getPaymentStaus().equals("paid")) {
				for(Passenger passenger : passengerList){
					logger.info("Creating ticket for "+passenger.getFirstName());
					ETicket eTicket = new ETicket();
					eTicket.setTicketId(genrateUniqueRandomId());
					eTicket.setBookingID(bookingid);
					eTicket.setPassangerName(passenger.getFirstName()+" "+passenger.getLastName());
					eTicket.setFlighID(booking.getBody().getData().getFlight().getFlightId());
					eTicket.setAirline(booking.getBody().getData().getFlight().getAirline());
					eTicket.setDeparture(booking.getBody().getData().getFlight().getDeparture());
					eTicket.setDepDate(booking.getBody().getData().getFlight().getDepartureDateAndTime());
					eTicket.setDestination(booking.getBody().getData().getFlight().getDestination());
					eTicket.setSeatNo(getSeatNumberFormatted(seatList.get(i).getSeatRowNumber(), seatList.get(i).getSeatNumber()));
					i++;
					fareRepository.save(eTicket);
					eTicketList.add(eTicket);	
				}
				return eTicketList;
				}
			else {
				throw new Exception("Payment Not Completed");
			}
			
	}

	@Override
	public ETicket getEticket(String ticketId) throws InvalidIdException {
		Optional<ETicket> eTicket = fareRepository.findById(ticketId);
		if(eTicket.isEmpty()) {
			throw new InvalidIdException("Invalid ticket Id");
		}
		return eTicket.get();
	}

	@Override
	public List<ETicket> getEticketsByBookingId(int bookingId) throws InvalidIdException {
		List<ETicket> eTicketList = fareRepository.findAllByBookingID(bookingId);
		if(eTicketList.isEmpty()) {
			throw new InvalidIdException("Invalid ticket Id");
		}
		return eTicketList;
		
	}
	
	
	public String genrateUniqueRandomId() {
		Random random = new Random();
		String id = String.valueOf(random.nextInt(999999)+1);
		
		Optional<ETicket> eTicket = fareRepository.findById(id);
		if(eTicket.isPresent()) {
			id = genrateUniqueRandomId();
		}
		
		return id;
	}

	@Override
	public List<ETicket> getAllEticket() {
	
		return fareRepository.findAll();
	}
	
	public String getSeatNumberFormatted(Integer row, Integer column) {
		int ch = 65;
		String seatNumber = String.valueOf(row + 1) + String.valueOf( (char)(ch + column)) ;
		return seatNumber;
	}

}
