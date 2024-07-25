package com.fare.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eticket")
public class ETicket {
	
	@Id
	private String ticketId;
	private int bookingID;
	private String passangerName;
	private int flighID;
	private String airline;
	private LocalDateTime depDate;
	private String seatNo;
	private String departure;
	private String destination;
	
}
