package com.multicloud.citizens.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NineDigitLongValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NineDigitLong {
    String message() default "Must be a 9-digit number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
