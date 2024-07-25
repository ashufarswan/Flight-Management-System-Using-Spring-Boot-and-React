package com.microservice.flights.models;
 
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.microservice.flights.exception.ValidAirlineType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="FlightDetails")
public class Flight {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer flightId;
	
	
	@NotEmpty(message = "Departure value cannot be null or empty")
	@Column(name="departure")
	private String departure;
	
	
	@NotEmpty(message = "Destination value cannot be null or empty")
	@Column(name="destination")
	private String destination;
	
	@NotNull(message = "Departure Date And Time cannot be null")
	@Column(name="departure_time")
	private LocalDateTime departureDateAndTime;
	
	@NotNull(message = "Arrival Date And Time cannot be null")
	@Column(name="arrival_time")
	private LocalDateTime arrivalDateAndTime;
	
	@Min(value = 10, message = "Duration Time cannot be less than 10" )
	@Column(name="duration")
	private Long duration;
	
	@NotEmpty(message = "AirLine Name cannot be null or empty")
	@Column(name="airline")
	private String airline;
	
	@ValidAirlineType
	@Column(name="airline_type")
	private String airlineType;
	
	
	@NotNull(message = "Price cannot be null")
	@DecimalMin(value = "500.0", message = "Price value cannot be less than 500")
	@Column(name = "price")
	private Double price;
	
	@ElementCollection
    @CollectionTable(name = "flight_booking_ids", joinColumns = @JoinColumn(name = "flight_id"))
    @Column(name = "booking_id")
	private List<Integer> bookingIdList;
	

}