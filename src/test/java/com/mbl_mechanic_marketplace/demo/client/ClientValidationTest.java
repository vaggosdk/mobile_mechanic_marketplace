package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void acceptsPersonClientWithRequiredDetails() {
        Client client = new Client();
        client.setEntityType(ClientEntityType.PERSON);
        client.setPerson(personDetails());

        assertTrue(validator.validate(client).isEmpty());
    }

    @Test
    void acceptsCompanyClientWithContactPersonDetails() {
        CompanyDetailsDto company = new CompanyDetailsDto();
        company.setCompanyName("Example Ltd");
        company.setTaxId("TAX-123");
        company.setContactPerson(personDetails());

        Client client = new Client();
        client.setEntityType(ClientEntityType.LEGAL_ENTITY);
        client.setCompany(company);

        assertTrue(validator.validate(client).isEmpty());
    }

    @Test
    void rejectsMissingOrMismatchedEntityDetails() {
        Client personWithoutDetails = new Client();
        personWithoutDetails.setEntityType(ClientEntityType.PERSON);

        Client companyWithPersonDetails = new Client();
        companyWithPersonDetails.setEntityType(ClientEntityType.LEGAL_ENTITY);
        companyWithPersonDetails.setPerson(personDetails());

        assertFalse(validator.validate(personWithoutDetails).isEmpty());
        assertFalse(validator.validate(companyWithPersonDetails).isEmpty());
    }

    @Test
    void rejectsMissingRequiredPersonFields() {
        Client client = new Client();
        client.setEntityType(ClientEntityType.PERSON);
        client.setPerson(new PersonDetailsDto());

        assertFalse(validator.validate(client).isEmpty());
    }

    @Test
    void rejectsPersonUnderMinimumAge() {
        PersonDetailsDto person = personDetails();
        person.setBirthDate(LocalDate.now().minusYears(6));

        assertTrue(validator.validate(person).stream()
                .anyMatch(error -> error.getPropertyPath().toString().equals("birthDate")
                        && error.getMessage().equals("The person must be at least 18 years old.")));
    }

    @Test
    void acceptsPersonOnEighteenthBirthday() {
        PersonDetailsDto person = personDetails();
        person.setBirthDate(LocalDate.now().minusYears(18));

        assertTrue(validator.validate(person).isEmpty());
    }

    @Test
    void rejectsUnderageCompanyContactPerson() {
        CompanyDetailsDto company = new CompanyDetailsDto();
        company.setCompanyName("Example Ltd");
        company.setTaxId("TAX-123");

        PersonDetailsDto contactPerson = personDetails();
        contactPerson.setBirthDate(LocalDate.now().minusYears(6));
        company.setContactPerson(contactPerson);

        Client client = new Client();
        client.setEntityType(ClientEntityType.LEGAL_ENTITY);
        client.setCompany(company);

        assertTrue(validator.validate(client).stream()
                .anyMatch(error -> error.getPropertyPath().toString().equals("company.contactPerson.birthDate")
                        && error.getMessage().equals("The person must be at least 18 years old.")));
    }

    private PersonDetailsDto personDetails() {
        PersonDetailsDto person = new PersonDetailsDto();
        person.setFirstName("Alex");
        person.setLastName("Taylor");
        person.setBirthDate(LocalDate.of(1990, 1, 1));
        return person;
    }
}
