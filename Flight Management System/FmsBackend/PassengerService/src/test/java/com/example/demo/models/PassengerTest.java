package com.example.demo.models;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
 
import java.util.Date;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
public class PassengerTest {
 
    private Passenger passenger;
 
    @BeforeEach
    public void setUp() {
        passenger = new Passenger();
        passenger.setPassengerId(1);
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setEmailId("john.doe@example.com");
        passenger.setPhoneNumber("1234567890");
        passenger.setDateOfBirth(new Date());
        passenger.setNationality("American");
        passenger.setPassportIdNumber("P1234567");
        passenger.setGender("Male");
        passenger.setSeatPreference("Aisle");
    }
 
    @Test
    public void testPassengerNotNull() {
        assertNotNull(passenger);
    }
 
    @Test
    public void testPassengerId() {
        assertEquals(1, passenger.getPassengerId());
    }
 
    @Test
    public void testFirstName() {
        assertEquals("John", passenger.getFirstName());
    }
 
    @Test
    public void testLastName() {
        assertEquals("Doe", passenger.getLastName());
    }
 
    @Test
    public void testEmailId() {
        assertEquals("john.doe@example.com", passenger.getEmailId());
    }
 
    @Test
    public void testPhoneNumber() {
        assertEquals("1234567890", passenger.getPhoneNumber());
    }
 
    @Test
    public void testDateOfBirth() {
        assertNotNull(passenger.getDateOfBirth());
    }
 
    @Test
    public void testNationality() {
        assertEquals("American", passenger.getNationality());
    }
 
    @Test
    public void testPassportIdNumber() {
        assertEquals("P1234567", passenger.getPassportIdNumber());
    }
 
    @Test
    public void testGender() {
        assertEquals("Male", passenger.getGender());
    }
 
    @Test
    public void testSeatPreference() {
        assertEquals("Aisle", passenger.getSeatPreference());
    }
}