package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Requires a date value to represent someone who has reached the configured
 * minimum age. Apply this alongside {@code @NotNull} when the date is required.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
public @interface MinimumAge {
    /** The minimum age, in years, that the person must have reached. */
    int value();

    /** The validation message to report when the age requirement is not met. */
    String message() default "The person must be at least 18 years old.";

    /** Validation groups this constraint belongs to. */
    Class<?>[] groups() default {};

    /** Metadata for clients of the Bean Validation API. */
    Class<? extends Payload>[] payload() default {};
}
