package com.mbl_mechanic_marketplace.demo.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientRequestExceptionHandlerTest {
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CreateClientController())
            .setControllerAdvice(new ClientRequestExceptionHandler())
            .build();

    @Test
    void explainsMissingPersonFields() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "entityType": "PERSON",
                                  "person": {}
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The request contains invalid fields."))
                .andExpect(jsonPath("$.errors[?(@.field == 'person.firstName')].message")
                        .value(org.hamcrest.Matchers.hasItem("First name is required for a person.")))
                .andExpect(jsonPath("$.errors[?(@.field == 'person.lastName')].message")
                        .value(org.hamcrest.Matchers.hasItem("Last name is required for a person.")))
                .andExpect(jsonPath("$.errors[?(@.field == 'person.birthDate')].message")
                        .value(org.hamcrest.Matchers.hasItem("Birthdate is required for a person.")));
    }

    @Test
    void rejectsPersonUnderMinimumAgeOverHttp() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "entityType": "PERSON",
                                  "person": {
                                    "firstName": "Alex",
                                    "lastName": "Taylor",
                                    "birthDate": "2020-01-01"
                                  }
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == 'person.birthDate')].message")
                        .value(org.hamcrest.Matchers.hasItem("The person must be at least 18 years old.")));
    }

    @Test
    void explainsUnsupportedEntityType() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"entityType":"OTHER"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(org.hamcrest.Matchers.containsString("entityType must be PERSON or LEGAL_ENTITY")))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    void explainsMismatchedEntityDetailsAtRequestLevel() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "entityType": "PERSON",
                                  "person": {
                                    "firstName": "Alex",
                                    "lastName": "Taylor",
                                    "birthDate": "1990-01-01"
                                  },
                                  "company": {
                                    "companyName": "Example Ltd",
                                    "taxId": "TAX-123",
                                    "contactPerson": {
                                      "firstName": "Alex",
                                      "lastName": "Taylor",
                                      "birthDate": "1990-01-01"
                                    }
                                  }
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == 'company')].message")
                        .value(org.hamcrest.Matchers.hasItem(
                                "Company details are not allowed when entityType is PERSON."
                        )));
    }

    @Test
    void explainsMissingPersonNode() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"entityType":"PERSON"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == 'person')].message")
                        .value(org.hamcrest.Matchers.hasItem(
                                "Person details are required when entityType is PERSON."
                        )));
    }

    @Test
    void explainsMissingCompanyNode() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"entityType":"LEGAL_ENTITY"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[?(@.field == 'company')].message")
                        .value(org.hamcrest.Matchers.hasItem(
                                "Company details are required when entityType is LEGAL_ENTITY."
                        )));
    }

    @Test
    void explainsMissingCompanyContactPersonOnce() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "entityType": "LEGAL_ENTITY",
                                  "company": {
                                    "companyName": "Example Ltd",
                                    "taxId": "TAX-123"
                                  }
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0].field").value("company.contactPerson"))
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Contact person details are required within company."));
    }
}
