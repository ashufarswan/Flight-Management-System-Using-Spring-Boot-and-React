package com.example.demo.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenderValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {
    String message() default "Invalid gender. Valid options are 'Male', 'Female', or 'Others'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

