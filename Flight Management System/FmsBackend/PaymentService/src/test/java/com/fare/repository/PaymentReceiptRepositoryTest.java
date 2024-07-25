package com.fare.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fare.model.PaymentReceipt;

@ExtendWith(MockitoExtension.class)
public class PaymentReceiptRepositoryTest {

    @Mock
    private PaymentReceiptRepository paymentReceiptRepository;

   
    @Test
    public void testFindByBookingId() {
        // Mocking data
        int bookingId = 123;
        List<PaymentReceipt> expectedReceipts = new ArrayList<>();
        expectedReceipts.add(new PaymentReceipt());
        expectedReceipts.add(new PaymentReceipt());

        // Mocking repository method
        when(paymentReceiptRepository.findByBookingId(bookingId)).thenReturn(Optional.of(expectedReceipts));

        // Calling repository method
        Optional<List<PaymentReceipt>> receiptsOptional = paymentReceiptRepository.findByBookingId(bookingId);

        // Verifying the result
        assertEquals(true, receiptsOptional.isPresent());
        assertEquals(expectedReceipts, receiptsOptional.get());

        // Verifying that the repository method was called exactly once
        verify(paymentReceiptRepository, times(1)).findByBookingId(bookingId);
    }

}
