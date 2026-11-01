package com.mbl_mechanic_marketplace.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "entities", name = "person")
public class PersonJPA {

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "registration_date")
    private String registrationDate;
}
