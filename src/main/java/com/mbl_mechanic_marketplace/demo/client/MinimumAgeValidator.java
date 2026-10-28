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

        if(!isBirthDateInThePast(birthDate, context)){
            return false;
        }

        return isPersonAtLeastMinimumAge(birthDate, context);
//        return isBirthDateInThePast(birthDate, context) && isPersonAtLeastMinimumAge(birthDate, context);
    }

    private boolean isPersonAtLeastMinimumAge(LocalDate birthDate, ConstraintValidatorContext context) {
        boolean valid = true;

        LocalDate today = LocalDate.now();
        LocalDate minimumBirthDate = today.minusYears(minimumAge);
        if (birthDate.isAfter(minimumBirthDate)) {
            valid = false;
        }
        return valid;
    }

    private boolean isBirthDateInThePast(LocalDate birthDate, ConstraintValidatorContext context) {
        boolean valid = true;

        if (birthDate == null) {
            return true; // Null is considered valid; use @NotNull for required fields
        }

        if (birthDate.isAfter(LocalDate.now())) {
            context.disableDefaultConstraintViolation();
            addViolation(context, "birthDate", "Birthdate cannot be in the future.");
            valid = false;
        }
        return valid;
    }

    private void addViolation(ConstraintValidatorContext context, String property, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
