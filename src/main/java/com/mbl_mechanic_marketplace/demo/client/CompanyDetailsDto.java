package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Company details and the personal details of its contact person. */
@Getter
@Setter
@NoArgsConstructor
public class CompanyDetailsDto {
    @NotBlank(message = "Company name is required.")
    private String companyName;

    @NotBlank(message = "Tax id is required for a company.")
    private String taxId;

    @NotNull(message = "Contact person details are required within company.")
    @Valid
    private PersonDetailsDto contactPerson;
}
