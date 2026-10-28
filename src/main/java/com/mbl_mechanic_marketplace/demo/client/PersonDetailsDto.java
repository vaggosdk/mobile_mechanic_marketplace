package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Personal details required for an individual client or a company's contact
 * person. When Bean Validation cascades into this DTO, the birthdate must
 * also satisfy the configured {@link MinimumAge} constraint.
 */
@Getter
@Setter
@NoArgsConstructor
public class PersonDetailsDto {
    @NotBlank(message = "First name is required for a person.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required for a person.")
    private String lastName;

    @NotNull(message = "Birthdate is required for a person.")
    @MinimumAge(value = 18)
    private LocalDate birthDate;

    private Sex sex;

    private String taxId;
}
