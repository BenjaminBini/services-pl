package com.sully.covid.dal.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name="CENTRE_ROUTIER")
public class CentreRoutier {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NOM_CENTRE")
    private String nomCentre;

    @Column(name = "DEPARTEMENT")
    private String departement;

    @Column(name = "VOIE")
    private String voie;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "COM")
    private String com;

    @Column(name = "Lat")
    private String lat;

    @Column(name = "Lon")
    private String lon;

    @Column(name = "STATUT_OUVERT")
    private Boolean statutOuvert;
}
