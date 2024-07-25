package com.flight.booking.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flight.booking.exception.ValidBoolean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "booking_table")
public class Booking {
	
	@Id
	@Column(name = "booking_id")
	private Integer bookingId;
	
//	@NotNull
//	@Column(name = "user_id")
//	private String userId;
	
	@Transient
	private Flight flight;
	
	
	
	@NotNull(message = "Number of passengers cannot be null")
    @Min(value = 1, message = "Number of passengers cannot be 0 or negative")
	@Column(name = "number_of_passengers")
	private Integer numberOfPassengers;
	
	
	@Transient
	private List<Passenger> passengerList;
	
	@Column(name = "user_id")
	@NotNull(message = "User Id cannot be null")
	private String userId;
	
	@CreatedDate
	@Column(name = "booking_date_time")
	private Date bookingDateAndTime;
	
	
	
	@NotNull(message = "Seat List must not be null")
    @Valid // This will trigger validation of the Seat entity
    @OneToMany(mappedBy = "bookingObj", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Seat> seatList = new ArrayList<>();
	
	
	
//	@ValidBoolean(message = "Booking status must be either true or false")
	@Column(name = "booking_status")
	private String bookingStatus;
	
	

	
	
	public void updateSeatList(List<Seat> newSeatList) {
		// Detach old seats from the booking
		// owner relationship has to be managed explicitly
        if (this.seatList != null) {
            for (Seat seat : this.seatList) {
                seat.setBookingObj(null);
            }
        }

        // Updated the seatList to the new list Of BookingObject
        this.seatList = newSeatList;

        // Attaching new seats to the booking
        // owner relationship has to be managed explicitly
        if (this.seatList != null) {
            for (Seat seat : this.seatList) {
                seat.setBookingObj(this);
            }
        }
	}
	
	
	
}
