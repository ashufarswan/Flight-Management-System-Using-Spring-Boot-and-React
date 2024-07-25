package com.flight.booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flight.booking.exception.ValidBoolean;
import com.flight.booking.exception.ValidClassType;
import com.flight.booking.exception.ValidSeatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seat_table")
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seat_id")
	private Integer seatId;
	
	
	@Min(value = 0, message = "Seat Number cannot be negative number")
	@Column(name="seat_number")
	private Integer seatNumber;
	
	
	@Min(value = 0, message = "Row Number cannot be negative number")
	@Column(name="seat_row_number")
	private Integer seatRowNumber;
	
	
	@NotNull(message = "Class type cannot be null")
	@ValidClassType
	@Column(name="seat_class_type")
	private String seatClassType; /*Economy, Business, First*/
	
	@ValidBoolean(message = "occupied field must be either true or false")
	@Column(name="is_occupied")
	private Boolean isOccupied;
	
	
	@ValidSeatType
	@Column(name = "seat_type")
	private String seatType; /*Window, Aisle, Middle*/
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	@JsonBackReference
	private Booking bookingObj;
	
}
