package com.microservice.flights.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class AirlineTypeValidator implements ConstraintValidator<ValidAirlineType, String> {

    private final List<String> validAirlineTypes = Arrays.asList("domestic", "international");

    @Override
    public void initialize(ValidAirlineType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // You might want to handle null separately with @NotNull
        }
        return validAirlineTypes.contains(value.toLowerCase());
    }
}
