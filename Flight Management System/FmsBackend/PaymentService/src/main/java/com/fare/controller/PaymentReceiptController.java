package com.fare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fare.exception.BookingStatusDoneException;
import com.fare.exception.InvalidIdException;
import com.fare.model.PaymentReceipt;
import com.fare.model.Seat;
import com.fare.service.PaymentReceiptServiceImpl;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/payment")
public class PaymentReceiptController {
	
	private Logger logger = LoggerFactory.getLogger(PaymentReceiptController.class);
	
	@Autowired
	PaymentReceiptServiceImpl paymentReceiptServiceImpl;
	
	
	@PostMapping("/createOrder/{bookingid}")
	public ResponseEntity<PaymentReceipt> createOrder(@PathVariable("bookingid") int id,@RequestBody List<Seat> seatList) throws InvalidIdException, RazorpayException, BookingStatusDoneException{
		logger.info("creating order !!!");
		PaymentReceipt paymentReceipt =  paymentReceiptServiceImpl.createOrder(id,seatList);
	    return new ResponseEntity<>(paymentReceipt,HttpStatus.OK);
	}
	
	@GetMapping("/updateOrder/{orderId}")
	public ResponseEntity<PaymentReceipt> updateOrder(@PathVariable String orderId) throws InvalidIdException{
		logger.info("updating status");
		PaymentReceipt paymentReceipt =  paymentReceiptServiceImpl.updateOrder(orderId);
	    return new ResponseEntity<>(paymentReceipt,HttpStatus.OK);
	}
	
	
	@GetMapping("/getReceipt/{id}")
	public ResponseEntity<PaymentReceipt> getReceipt(@PathVariable String id) throws InvalidIdException{
		logger.info("Fetching Receipt !!!");
		PaymentReceipt paymentReceipt =  paymentReceiptServiceImpl.getReceipt(id);
	    return new ResponseEntity<>(paymentReceipt,HttpStatus.OK);
	}
	
	@GetMapping("/getReceipt/bookingid/{id}")
	public ResponseEntity<List<PaymentReceipt>> getReceipt(@PathVariable int id) throws InvalidIdException{
		logger.info("Fetching Receipt !!!");
		List<PaymentReceipt> paymentReceipt =  paymentReceiptServiceImpl.getReceiptByBookingId(id);
	    return new ResponseEntity<>(paymentReceipt,HttpStatus.OK);
	}
	
	@GetMapping("/getReceipts")
	public ResponseEntity<List<PaymentReceipt>> getReceipt(){
		logger.info("Fetching Receipt !!!");
		List<PaymentReceipt> paymentReceipt =  paymentReceiptServiceImpl.getAllReceipt();
	    return new ResponseEntity<>(paymentReceipt,HttpStatus.OK);
	}
	
}
