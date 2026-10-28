package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * Checks {@link MinimumAge} constraints on birth dates. A null date is left
 * valid here so a separate {@code @NotNull} constraint can report that it is
 * missing.
 */
public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    private int minimumAge;

    /** Reads the configured minimum age from the {@link MinimumAge} annotation. */
    @Override
    public void initialize(MinimumAge constraintAnnotation) {
        this.minimumAge = constraintAnnotation.value();
    }

    /**
     * Accepts dates on or before the latest birthdate that meets the minimum
     * age, which includes someone on their birthday.
     */
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return true;
        }

        LocalDate minimumBirthDate = LocalDate.now().minusYears(minimumAge);
        return birthDate.isBefore(minimumBirthDate) || birthDate.isEqual(minimumBirthDate);
    }
}
