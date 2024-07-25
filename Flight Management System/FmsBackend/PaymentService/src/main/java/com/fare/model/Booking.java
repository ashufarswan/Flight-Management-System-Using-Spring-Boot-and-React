package com.fare.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
		
		private Integer bookingId;
		private Flight flight;
		private Integer numberOfPassengers;
		private List<Passenger> passengerList;
		private String userId;
		private Date bookingDateAndTime;
		private List<Seat> seatList = new ArrayList<>();
		private String bookingStatus;
}

