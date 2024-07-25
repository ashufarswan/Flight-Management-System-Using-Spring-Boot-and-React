package com.example.demo.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PassportNumberValidator implements ConstraintValidator<ValidPassportNumber, String> {

    @Override
    public void initialize(ValidPassportNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.matches("[a-zA-Z0-9]{6,9}");
    }
}

