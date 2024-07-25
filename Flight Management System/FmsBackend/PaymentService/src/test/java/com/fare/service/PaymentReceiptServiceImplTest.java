package com.fare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.fare.exception.BookingStatusDoneException;
import com.fare.exception.InvalidIdException;
import com.fare.model.ApiResponse;
import com.fare.model.Booking;
import com.fare.model.PaymentReceipt;
import com.fare.repository.PaymentReceiptRepository;

public class PaymentReceiptServiceImplTest {

    @Mock
    private PaymentReceiptRepository paymentReceiptRepository;

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private PaymentReceiptServiceImpl paymentReceiptService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReceipt_ValidId_ReturnsPaymentReceipt() throws InvalidIdException {
        String paymentId = "valid_payment_id";
        PaymentReceipt paymentReceipt = createMockPaymentReceipt(paymentId);
        when(paymentReceiptRepository.findById(paymentId)).thenReturn(Optional.of(paymentReceipt));

        PaymentReceipt result = paymentReceiptService.getReceipt(paymentId);

        assertNotNull(result);
        assertEquals(paymentId, result.getPaymentId());
    }

    @Test
    public void testGetReceipt_InvalidId_ThrowsInvalidIdException() {
        String invalidPaymentId = "invalid_payment_id";
        when(paymentReceiptRepository.findById(invalidPaymentId)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            paymentReceiptService.getReceipt(invalidPaymentId);
        });
    }

   

    @Test
    public void testCreateOrder_InvalidBookingId_ThrowsInvalidIdException() {
        int invalidBookingId = -1;
        ResponseEntity<ApiResponse<Booking>> bookingResponse = ResponseEntity.ok(new ApiResponse<>("failed", "failed", null));
        when(bookingClient.getBookingById(invalidBookingId)).thenReturn(bookingResponse);

        assertThrows(InvalidIdException.class, () -> {
            paymentReceiptService.createOrder(invalidBookingId,null);
        });
    }

    @Test
    public void testCreateOrder_BookingAlreadyDone_ThrowsBookingStatusDoneException() {
        int bookingId = 1;
        Booking booking = createMockBooking(bookingId);
        booking.setBookingStatus("paid"); // Set booking status to true (already done)
        ResponseEntity<ApiResponse<Booking>> bookingResponse = ResponseEntity.ok(new ApiResponse<>("Done", "success", booking));
        when(bookingClient.getBookingById(bookingId)).thenReturn(bookingResponse);

        assertThrows(BookingStatusDoneException.class, () -> {
            paymentReceiptService.createOrder(bookingId,null);
        });
    }

    @Test
    public void testUpdateOrder_ValidOrderId_UpdatesPaymentStatusAndBookingStatus() throws InvalidIdException {
        String orderId = "valid_order_id";
        PaymentReceipt paymentReceipt = createMockPaymentReceipt(orderId);
        when(paymentReceiptRepository.findById(orderId)).thenReturn(Optional.of(paymentReceipt));

        Booking booking = createMockBooking(paymentReceipt.getBookingId());
        ResponseEntity<ApiResponse<Booking>> bookingResponse = ResponseEntity.ok(new ApiResponse<>("Done", "success", booking));
        when(bookingClient.getBookingById(paymentReceipt.getBookingId())).thenReturn(bookingResponse);

        PaymentReceipt updatedReceipt = paymentReceiptService.updateOrder(orderId);

        assertNotNull(updatedReceipt);
        assertEquals("paid", updatedReceipt.getPaymentStaus());
        verify(paymentReceiptRepository, times(1)).save(paymentReceipt);
        verify(bookingClient, times(1)).updateBooking(paymentReceipt.getBookingId(), booking);
    }

    @Test
    public void testUpdateOrder_InvalidOrderId_ThrowsInvalidIdException() {
        String invalidOrderId = "invalid_order_id";
        when(paymentReceiptRepository.findById(invalidOrderId)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            paymentReceiptService.updateOrder(invalidOrderId);
        });
    }

    
    @Test
    public void testGetReceiptByBookingId_InvalidBookingId_ThrowsInvalidIdException() {
        int invalidBookingId = -1;
        when(paymentReceiptRepository.findByBookingId(invalidBookingId)).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            paymentReceiptService.getReceiptByBookingId(invalidBookingId);
        });
    }

    // Helper methods...

    private Booking createMockBooking(int bookingId) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setBookingStatus("created"); // Assume booking is not done initially
        
        return booking;
    }

    private PaymentReceipt createMockPaymentReceipt(String paymentId) {
        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setPaymentId(paymentId);
        paymentReceipt.setBookingId(1); // Assuming booking ID for testing
        paymentReceipt.setPaymentStaus("pending"); // Initial payment status
        return paymentReceipt;
    }

    // Other helper methods...

}
