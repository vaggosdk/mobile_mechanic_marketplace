package com.mbl_mechanic_marketplace.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "entities", name = "person")
public class PersonJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "tax_id", unique = true)
    private String taxId;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
}
