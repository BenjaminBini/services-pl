package com.sully.covid.dal.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "CENTRE_CT")
public class CentreCT implements ModelBase {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "STATUT_OUVERT")
    private Boolean statutOuvert;

    @Column(name = "CODE_AGREM")
    private String codeAgrem;

    @Column(name = "DEP")
    private String dep;

    @Column(name = "CODE_INSEE")
    private String codeInsee;

    @Column(name = "COMMUNE")
    private String commune;

    @Column(name = "ADRESSE_L1")
    private String adresseLigne1;

    @Column(name = "ADRESSE_L2")
    private String adresesLigne2;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "Lat")
    private String lat;

    @Column(name = "Lon")
    private String lon;
}
