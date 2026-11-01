package com.mbl_mechanic_marketplace.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "entities", name = "company")
public class CompanyJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_person_id")
    private Long contactPersonId;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
}
