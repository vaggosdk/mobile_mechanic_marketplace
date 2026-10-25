package com.mbl_mechanic_marketplace.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "entities", name = "client")
public class ClientJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "registration_date")
    private LocalDate registrationDate;
}
