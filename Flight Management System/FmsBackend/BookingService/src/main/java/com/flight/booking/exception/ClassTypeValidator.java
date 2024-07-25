package com.flight.booking.exception;

import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClassTypeValidator implements ConstraintValidator<ValidClassType, String> {
    private static final Set<String> VALID_CLASS_TYPES = Set.of("Economy", "Business", "First");

    @Override
    public void initialize(ValidClassType constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && VALID_CLASS_TYPES.contains(value);
    }
}
