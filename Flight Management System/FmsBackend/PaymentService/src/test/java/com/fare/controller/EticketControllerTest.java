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

import com.fare.exception.InvalidIdException;
import com.fare.model.ETicket;
import com.fare.service.EticketService;

@ExtendWith(MockitoExtension.class)
public class EticketControllerTest {

    @Mock
    private EticketService fareService;

    @InjectMocks
    private EticketController eticketController;


    @Test
    public void testCreateEticket() throws InvalidIdException, Exception {
        // Mocking data
        int bookingId = 123;
        List<ETicket> expectedEticketList = new ArrayList<>();
        ETicket eticket1 = new ETicket();
        ETicket eticket2 = new ETicket();
        expectedEticketList.add(eticket1);
        expectedEticketList.add(eticket2);

        // Mocking service method
        when(fareService.createEticket(bookingId)).thenReturn(expectedEticketList);

        // Calling controller method
        ResponseEntity<List<ETicket>> response = eticketController.createEticket(bookingId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEticketList, response.getBody());

        // Verifying that the service method was called exactly once
        verify(fareService, times(1)).createEticket(bookingId);
    }

    @Test
    public void testGetTicket() throws InvalidIdException {
        // Mocking data
        String ticketId = "T123";
        ETicket expectedEticket = new ETicket();

        // Mocking service method
        when(fareService.getEticket(ticketId)).thenReturn(expectedEticket);

        // Calling controller method
        ResponseEntity<ETicket> response = eticketController.getTicket(ticketId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEticket, response.getBody());

        // Verifying that the service method was called exactly once
        verify(fareService, times(1)).getEticket(ticketId);
    }

    @Test
    public void testGetAllTicket() {
        // Mocking data
        List<ETicket> expectedEticketList = new ArrayList<>();
        ETicket eticket1 = new ETicket();
        ETicket eticket2 = new ETicket();
        expectedEticketList.add(eticket1);
        expectedEticketList.add(eticket2);

        // Mocking service method
        when(fareService.getAllEticket()).thenReturn(expectedEticketList);

        // Calling controller method
        ResponseEntity<List<ETicket>> response = eticketController.getAllTicket();

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEticketList, response.getBody());

        // Verifying that the service method was called exactly once
        verify(fareService, times(1)).getAllEticket();
    }

    @Test
    public void testGetTicketByBookingId() throws InvalidIdException {
        // Mocking data
        int bookingId = 123;
        List<ETicket> expectedEticketList = new ArrayList<>();
        ETicket eticket1 = new ETicket();
        ETicket eticket2 = new ETicket();
        expectedEticketList.add(eticket1);
        expectedEticketList.add(eticket2);

        // Mocking service method
        when(fareService.getEticketsByBookingId(bookingId)).thenReturn(expectedEticketList);

        // Calling controller method
        ResponseEntity<List<ETicket>> response = eticketController.getTicketByBokingID(bookingId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEticketList, response.getBody());

        // Verifying that the service method was called exactly once
        verify(fareService, times(1)).getEticketsByBookingId(bookingId);
    }
}
