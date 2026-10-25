package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** HTTP endpoints for client operations. */
@RestController
@RequestMapping("/api/clients")
public class CreateClientController {
    public CreateClientController() {
    }

    /** Accepts and validates a client creation request. Persistence is not implemented yet. */
    @PostMapping
    public void createClient(@Valid @RequestBody Client client) {
        // Logic to create a client
        System.out.println("Created");
    }
}
