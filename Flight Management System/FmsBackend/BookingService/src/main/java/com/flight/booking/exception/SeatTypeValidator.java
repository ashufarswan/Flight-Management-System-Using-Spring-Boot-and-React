package com.flight.booking.exception;

import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SeatTypeValidator implements ConstraintValidator<ValidSeatType, String> {
    private static final Set<String> VALID_SEAT_TYPES = Set.of("Window", "Aisle", "Middle");

    @Override
    public void initialize(ValidSeatType constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && VALID_SEAT_TYPES.contains(value);
    }
}
