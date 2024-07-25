package com.example.demo.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    private final List<String> validGenders = Arrays.asList("Male", "Female", "Others");

    @Override
    public void initialize(ValidGender constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // You might want to handle null separately with @NotNull
        }
        return validGenders.contains(value);
    }
}
