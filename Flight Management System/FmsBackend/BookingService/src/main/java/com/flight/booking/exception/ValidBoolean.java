package com.flight.booking.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BooleanNotNullValidator.class)
public @interface ValidBoolean {
    String message() default "Boolean value must not be null and must be either true or false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
