package com.mbl_mechanic_marketplace.demo.client;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** HTTP endpoints for client operations. */
@RestController
@RequestMapping("/api/clients")
public class CreateClientController {
    public CreateClientController() {
    }

    /** Accepts and validates a client creation request. Persistence is not implemented yet. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createClient(@Valid @RequestBody Client client) {
        // Logic to create a client
        System.out.println("Created");
    }
}
