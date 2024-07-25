package com.flight.booking.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingViewPost {
	private Integer bookingId;
	private Integer flightId;
	private Integer passengerIdList;
	private List<Seat> seatList;
	private String bookingStatus;
	
}
