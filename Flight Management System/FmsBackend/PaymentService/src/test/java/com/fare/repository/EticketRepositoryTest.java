package com.fare.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fare.model.ETicket;

@ExtendWith(MockitoExtension.class)
public class EticketRepositoryTest {

    @Mock
    private EticketRepository eticketRepository;



    @Test
    public void testFindAllByBookingID() {
        // Mocking data
        int bookingId = 123;
        List<ETicket> expectedEticketList = new ArrayList<>();
        expectedEticketList.add(new ETicket());
        expectedEticketList.add(new ETicket());

        // Mocking repository method
        when(eticketRepository.findAllByBookingID(bookingId)).thenReturn(expectedEticketList);

        // Calling repository method
        List<ETicket> etickets = eticketRepository.findAllByBookingID(bookingId);

        // Verifying the result
        assertEquals(2, etickets.size());
        assertEquals(expectedEticketList, etickets);

        // Verifying that the repository method was called exactly once
        verify(eticketRepository, times(1)).findAllByBookingID(bookingId);
    }

    
}
