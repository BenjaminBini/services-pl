package com.sully.covid.dal.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "RELAIS_ROUTIER")
public class RelaisRoutier implements ModelBase {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "Lat")
    String lat;

    @Column(name = "Lon")
    String lon;

    @Column(name = "DEP")
    private String dep;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "STATUT_OUVERT")
    private Boolean statutOuvert;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "COM")
    private String com;
}
