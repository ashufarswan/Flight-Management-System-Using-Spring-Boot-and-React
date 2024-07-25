package com.example.demo.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.exception.ValidGender;
import com.example.demo.exception.ValidPassportNumber;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "passenger_table")
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Integer passengerId;
	
	@NotEmpty(message = "First Name of Passenger cannot be null")
	@Size(min = 3, message = "First Name cannot be less than 3 characters")
	@Column(name = "first_name")
	private String firstName;
	
	@NotEmpty(message = "Last Name of Passenger cannot be null or empty")
    @Size(min = 3, message = "Last Name cannot be less than 3 characters")
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email is not in standard email format")
	@Column(name = "email_id")
	private String emailId;
	
	@NotNull(message = "Phone number cannot be null")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
	private String phoneNumber;
	
	@NotNull
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@NotEmpty
	@Column(name = "nationality")
	private String nationality;
	
	@NotNull(message = "Passport number cannot be null")
    @ValidPassportNumber
	@Column(name = "passport_id_no")
	private String passportIdNumber;
	
	 @NotNull(message = "Gender cannot be null")
	 @ValidGender
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "seat_preference")
	private String seatPreference;
	
	
	@ElementCollection
	@CollectionTable(name = "passenger_booking_ids", joinColumns = @JoinColumn(name = "passenger_id"))
	@Column(name = "booking_id")
	private List<Integer> bookingIdList=new ArrayList<Integer>();
	
}
