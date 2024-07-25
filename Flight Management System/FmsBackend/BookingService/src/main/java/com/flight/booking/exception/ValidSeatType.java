package com.flight.booking.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SeatTypeValidator.class)
public @interface ValidSeatType {
    String message() default "Invalid seat type, seat Type can only be : {Window, Aisle, Middle}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}