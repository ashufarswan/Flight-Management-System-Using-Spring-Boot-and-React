package com.fare.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {
	private Integer flightId;
	private String departure;
	private String destination;
	private LocalDateTime departureDateAndTime;
	private LocalDateTime arrivalDateAndTime;
	private double duration;
	private String airline;
	private String airlineType;
	private double price;
	private List<Integer> bookingIdList;
	
}

