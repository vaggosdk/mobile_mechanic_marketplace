package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implements {@link ValidClientRequest}: PERSON requires person details and no
 * company details; LEGAL_ENTITY requires company details and no top-level
 * person details. A missing entity type is left to its {@code @NotNull}
 * constraint so that it gets a field-specific validation error.
 */
public class ClientRequestValidator implements ConstraintValidator<ValidClientRequest, Client> {
    @Override
    public boolean isValid(Client client, ConstraintValidatorContext context) {
        // The @NotNull constraint on entityType reports a missing type separately.
        if (client == null || client.getEntityType() == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        return switch (client.getEntityType()) {
            case PERSON -> validatePersonRequest(client, context);
            case LEGAL_ENTITY -> validateCompanyRequest(client, context);
        };
    }

    private boolean validatePersonRequest(Client client, ConstraintValidatorContext context) {
        boolean valid = true;
        if (client.getPerson() == null) {
            addViolation(context, "person", "Person details are required when entityType is PERSON.");
            valid = false;
        }
        if (client.getCompany() != null) {
            addViolation(context, "company", "Company details are not allowed when entityType is PERSON.");
            valid = false;
        }
        return valid;
    }

    private boolean validateCompanyRequest(Client client, ConstraintValidatorContext context) {
        boolean valid = true;
        if (client.getCompany() == null) {
            addViolation(context, "company", "Company details are required when entityType is LEGAL_ENTITY.");
            valid = false;
        }
        if (client.getPerson() != null) {
            addViolation(context, "person", "Person details are not allowed at the top level when entityType is LEGAL_ENTITY.");
            valid = false;
        }
        return valid;
    }

    private void addViolation(ConstraintValidatorContext context, String property, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }
}
