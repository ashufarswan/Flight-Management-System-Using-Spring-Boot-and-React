package com.fare.service;

import java.util.List;

import com.fare.exception.BookingStatusDoneException;
import com.fare.exception.InvalidIdException;
import com.fare.model.PaymentReceipt;
import com.fare.model.Seat;
import com.razorpay.RazorpayException;

public interface PaymentReceiptService {
		
		public PaymentReceipt createOrder(int bookingId,List<Seat> seatList) throws InvalidIdException, RazorpayException, BookingStatusDoneException;
		public List<PaymentReceipt> getAllReceipt();
		public List<PaymentReceipt> getReceiptByBookingId(int id) throws InvalidIdException;
		public PaymentReceipt getReceipt(String id) throws InvalidIdException;
		public PaymentReceipt updateOrder(String id) throws InvalidIdException;
}
