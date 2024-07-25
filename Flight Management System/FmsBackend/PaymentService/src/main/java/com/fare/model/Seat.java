package com.fare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
	
	private Integer seatId;
	private Integer seatNumber;
	private Integer seatRowNumber;
	private String seatClassType; /*Economy, Business, First*/
	private Boolean isOccupied;
	private String seatType; /*Window, Aisle, Middle*/
	private Booking bookingObj;
	
}
