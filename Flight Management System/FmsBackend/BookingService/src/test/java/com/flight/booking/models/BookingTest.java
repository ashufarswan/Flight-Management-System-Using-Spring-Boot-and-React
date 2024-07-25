package com.flight.booking.models;
 
 
 
import static org.assertj.core.api.Assertions.assertThat;
 
import java.util.ArrayList;
import java.util.Date;
 
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
 
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
 
@SpringBootTest
public class BookingTest {
 
    private Validator validator;
 
    public BookingTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
 
    @Test
    void whenAllFieldsValid_thenNoConstraintViolations() {
        Booking booking = new Booking(1, new Flight(), 2, new ArrayList<>(), "user1", new Date(), new ArrayList<>(),"paid");
        var violations = validator.validate(booking);
        assertThat(violations).isEmpty();
    }
 
    @Test
    void whenNumberOfPassengersIsNull_thenConstraintViolation() {
        Booking booking = new Booking(1, new Flight(), null, new ArrayList<>(), "user1", new Date(), new ArrayList<>(), "paid");
        var violations = validator.validate(booking);
        assertThat(violations).isNotEmpty();
    }
 
    @Test
    void whenNumberOfPassengersIsZero_thenConstraintViolation() {
        Booking booking = new Booking(1, new Flight(), 0, new ArrayList<>(), "user1", new Date(), new ArrayList<>(), "paid");
        var violations = validator.validate(booking);
        assertThat(violations).isNotEmpty();
    }
 
    @Test
    void whenUserIdIsNull_thenConstraintViolation() {
        Booking booking = new Booking(1, new Flight(), 2, new ArrayList<>(), null, new Date(), new ArrayList<>(), "paid");
        var violations = validator.validate(booking);
        assertThat(violations).isNotEmpty();
    }
 
    @Test
    void whenSeatListIsNull_thenConstraintViolation() {
        Booking booking = new Booking(1, new Flight(), 2, new ArrayList<>(), "user1", new Date(), null, "paid");
        var violations = validator.validate(booking);
        assertThat(violations).isNotEmpty();
    }
 
}
 