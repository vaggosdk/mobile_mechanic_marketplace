package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level Bean Validation constraint for the relationship between a
 * client's entity type and its nested details. Bean Validation invokes
 * {@link ClientRequestValidator} when a {@code Client} annotated with this
 * constraint is validated; the validator accepts only the matching
 * {@code person} or {@code company} payload.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientRequestValidator.class)
public @interface ValidClientRequest {
    String message() default "Provide person details for PERSON or company details for LEGAL_ENTITY, but not both";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
