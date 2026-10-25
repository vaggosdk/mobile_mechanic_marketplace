package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** Personal details required for an individual client or a company's contact person. */
@Getter
@Setter
@NoArgsConstructor
public class PersonDetailsDto {
    @NotBlank(message = "First name is required for a person.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required for a person.")
    private String lastName;

    @NotNull(message = "Birth date is required for a person.")
    private LocalDate birthDate;

    private Sex sex;

    private String taxId;
}
