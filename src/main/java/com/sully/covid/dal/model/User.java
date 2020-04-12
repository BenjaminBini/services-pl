package com.sully.covid.dal.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "GEST")
    private String gest;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

}