package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for creating a client. A PERSON request supplies {@code person};
 * a LEGAL_ENTITY request supplies {@code company} instead. The
 * {@link ValidClientRequest} constraint checks that these fields match the
 * selected entity type, while {@link Valid} cascades validation into the
 * supplied nested DTO.
 */
@Getter
@Setter
@NoArgsConstructor
@ValidClientRequest
public class Client {
    @NotNull
    private ClientEntityType entityType;

    @Valid
    private PersonDetailsDto person;

    @Valid
    private CompanyDetailsDto company;
}
