package com.fare.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fare.exception.BookingStatusDoneException;
import com.fare.exception.InvalidIdException;
import com.fare.model.PaymentReceipt;
import com.fare.service.PaymentReceiptServiceImpl;
import com.razorpay.RazorpayException;

@ExtendWith(MockitoExtension.class)
public class PaymentReceiptControllerTest {

    @Mock
    private PaymentReceiptServiceImpl paymentReceiptService;

    @InjectMocks
    private PaymentReceiptController paymentReceiptController;

   

    @Test
    public void testCreateOrder() throws InvalidIdException, RazorpayException, BookingStatusDoneException {
        // Mocking data and service method
        int bookingId = 123;
        PaymentReceipt expectedReceipt = new PaymentReceipt();

        when(paymentReceiptService.createOrder(bookingId,null)).thenReturn(expectedReceipt);

        // Calling controller method
        ResponseEntity<PaymentReceipt> response = paymentReceiptController.createOrder(bookingId,null);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReceipt, response.getBody());

        // Verifying that the service method was called exactly once
        verify(paymentReceiptService, times(1)).createOrder(bookingId,null);
    }

    @Test
    public void testUpdateOrder() throws InvalidIdException {
        // Mocking data and service method
        String orderId = "1";
        PaymentReceipt expectedReceipt = new PaymentReceipt();

        when(paymentReceiptService.updateOrder(orderId)).thenReturn(expectedReceipt);

        // Calling controller method
        ResponseEntity<PaymentReceipt> response = paymentReceiptController.updateOrder(orderId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReceipt, response.getBody());

        // Verifying that the service method was called exactly once
        verify(paymentReceiptService, times(1)).updateOrder(orderId);
    }

    @Test
    public void testGetReceiptWithStringId() throws InvalidIdException {
        // Mocking data and service method
        String receiptId = "1";
        PaymentReceipt expectedReceipt = new PaymentReceipt();

        when(paymentReceiptService.getReceipt(receiptId)).thenReturn(expectedReceipt);

        // Calling controller method
        ResponseEntity<PaymentReceipt> response = paymentReceiptController.getReceipt(receiptId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReceipt, response.getBody());

        // Verifying that the service method was called exactly once
        verify(paymentReceiptService, times(1)).getReceipt(receiptId);
    }

    @Test
    public void testGetReceiptWithIntId() throws InvalidIdException {
        // Mocking data and service method
        int bookingId = 123;
        List<PaymentReceipt> expectedReceipts = new ArrayList<>();
        expectedReceipts.add(new PaymentReceipt());
        expectedReceipts.add(new PaymentReceipt());

        when(paymentReceiptService.getReceiptByBookingId(bookingId)).thenReturn(expectedReceipts);

        // Calling controller method
        ResponseEntity<List<PaymentReceipt>> response = paymentReceiptController.getReceipt(bookingId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReceipts, response.getBody());

        // Verifying that the service method was called exactly once
        verify(paymentReceiptService, times(1)).getReceiptByBookingId(bookingId);
    }

    @Test
    public void testGetReceipts() {
        // Mocking data and service method
        List<PaymentReceipt> expectedReceipts = new ArrayList<>();
        expectedReceipts.add(new PaymentReceipt());
        expectedReceipts.add(new PaymentReceipt());

        when(paymentReceiptService.getAllReceipt()).thenReturn(expectedReceipts);

        // Calling controller method
        ResponseEntity<List<PaymentReceipt>> response = paymentReceiptController.getReceipt();

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReceipts, response.getBody());

        // Verifying that the service method was called exactly once
        verify(paymentReceiptService, times(1)).getAllReceipt();
    }
}
