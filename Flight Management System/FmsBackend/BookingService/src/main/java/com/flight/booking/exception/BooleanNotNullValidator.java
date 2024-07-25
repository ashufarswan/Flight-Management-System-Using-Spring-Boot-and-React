package com.flight.booking.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BooleanNotNullValidator implements ConstraintValidator<ValidBoolean, Boolean> {

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value != null; // ensures the value is not null
    }
}
