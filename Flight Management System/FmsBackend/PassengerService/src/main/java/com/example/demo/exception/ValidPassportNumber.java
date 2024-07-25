package com.example.demo.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PassportNumberValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassportNumber {
    String message() default "Invalid passport number. It should be between 6 to 9 characters long and contain only letters and digits.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

