package com.microservice.flights.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AirlineTypeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAirlineType {
    String message() default "Invalid airline type. Valid options are 'domestic' or 'international'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
