package com.flight.booking.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClassTypeValidator.class)
public @interface ValidClassType {
    String message() default "Invalid class type, class Type can only be : {Economy, Business, First}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
