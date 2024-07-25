package com.fare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fare.exception.BookingStatusDoneException;
import com.fare.exception.InvalidIdException;
import com.fare.model.ApiResponse;
import com.fare.model.Booking;
import com.fare.model.PaymentReceipt;
import com.fare.model.Seat;
import com.fare.repository.PaymentReceiptRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentReceiptServiceImpl implements PaymentReceiptService{
	
	private Logger logger = LoggerFactory.getLogger(PaymentReceiptServiceImpl.class);
	
	@Autowired
	PaymentReceiptRepository paymentReceiptRepository;
	
	@Autowired
	private BookingClient bookingClient;
	
	@Override
	public PaymentReceipt getReceipt(String id) throws InvalidIdException {
		Optional<PaymentReceipt> paymentReceipt = paymentReceiptRepository.findById(id);
		if(paymentReceipt.isEmpty()) throw new InvalidIdException("Invalid Payment Id");
		return paymentReceipt.get();
	}
	
	
	
	@Override
	public PaymentReceipt createOrder(int bookingId,List<Seat> seatList) throws InvalidIdException, RazorpayException, BookingStatusDoneException {
		
		
		logger.info("Calculating Fare....");
		ResponseEntity<ApiResponse<Booking>> booking = bookingClient.getBookingById(bookingId);
		
		//Check for Invalid Booking Id
		if(booking.getBody().getStatus().equals("failed")) {
			throw new InvalidIdException("Invalid booking id "+bookingId);
		}
		
		//Check if booking already done.
		if(booking.getBody().getData().getBookingStatus().equals("created")) {
			
			List<String> seatTypeList = seatList.stream().map(seatObj ->seatObj.getSeatType()).collect(Collectors.toList());
			System.out.println(booking.getBody().getData().getSeatList().size());
			double fare = 0.0;
			System.out.println(seatTypeList);
			for(String seatType : seatTypeList) {
				if(seatType.equalsIgnoreCase("Window")) {
					fare += booking.getBody().getData().getFlight().getPrice()+ booking.getBody().getData().getFlight().getPrice() * 0.05;
				}
				else {
					fare +=  booking.getBody().getData().getFlight().getPrice();
				}
			}
			System.out.println(fare);
			
			//Linking to RazorPay
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_LrHTWqr4yuxxA2","ddLMK949k57f2ldSsOzwhxaI");
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount",(int)fare*100);
			orderRequest.put("currency","INR");
			orderRequest.put("receipt", "receipt#1");
			Order order = razorpayClient.orders.create(orderRequest);
			
			
			logger.info("Creating Payment Recipt for Booking Id: "+bookingId);
			PaymentReceipt paymentReceipt = new PaymentReceipt();
			paymentReceipt.setPaymentId(order.get("id"));
			paymentReceipt.setBookingId(bookingId);
			paymentReceipt.setAmount((double) (Integer.parseInt( order.get("amount")+"")/100));
			paymentReceipt.setPaymentStaus(order.get("status"));
			paymentReceiptRepository.save(paymentReceipt);
			return paymentReceipt;
		}
		else {
			throw new BookingStatusDoneException("Booking Already Done!!!!!");
		}
		
	}
	

	public PaymentReceipt updateOrder(String id) throws InvalidIdException {
		Optional<PaymentReceipt> paymentReceipt = paymentReceiptRepository.findById(id);
		if(paymentReceipt.isEmpty()) {
			throw new InvalidIdException("Invalid order ID");
		}
		paymentReceipt.get().setPaymentStaus("paid");
		ResponseEntity<ApiResponse<Booking>> booking = bookingClient.getBookingById(paymentReceipt.get().getBookingId());
		booking.getBody().getData().setBookingStatus("paid");
		paymentReceiptRepository.save(paymentReceipt.get());
		bookingClient.updateBooking(paymentReceipt.get().getBookingId(), booking.getBody().getData());
		logger.info("At end of update order");
		return paymentReceipt.get();
	}
	

	@Override
	public List<PaymentReceipt> getAllReceipt() {
		
		return paymentReceiptRepository.findAll();
	}

	
	@Override
	public List<PaymentReceipt> getReceiptByBookingId(int id) throws InvalidIdException{
		
		Optional<List<PaymentReceipt>> paymentReceipt = paymentReceiptRepository.findByBookingId(id);
		if(paymentReceipt.isEmpty()) throw new InvalidIdException("Invalid Booking Id");
		return paymentReceipt.get();
	}


	


}
