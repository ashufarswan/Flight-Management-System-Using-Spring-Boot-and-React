package com.flight.booking.models;

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
public class Passenger {
	private  Integer passengerId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNumber;
	private Date dateOfBirth;
	private String nationality;
	private String passportIdNumber;
	private String gender;
	private String seatPreference;
	private List<Integer> bookingIdList;
	
	
}
