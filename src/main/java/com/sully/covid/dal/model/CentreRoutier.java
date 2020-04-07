package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "CENTRE_ROUTIER")
public class CentreRoutier implements ModelBase {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "ID")
    private long id;

    @Column(name = "TYPE")
    @CsvBindByName(column = "TYPE")
    private String type;

    @Column(name = "NOM_CENTRE")
    @CsvBindByName(column = "NOM_CENTRE")
    private String nomCentre;

    @Column(name = "DEPARTEMENT")
    @CsvBindByName(column = "DEPARTEMENT")
    private String departement;

    @Column(name = "VOIE")
    @CsvBindByName(column = "VOIE")
    private String voie;

    @Column(name = "ADRESSE")
    @CsvBindByName(column = "ADRESSE")
    private String adresse;

    @Column(name = "COM")
    @CsvBindByName(column = "COM")
    private String com;

    @Column(name = "Lat")
    @CsvBindByName(column = "Lat")
    private String lat;

    @Column(name = "Lon")
    @CsvBindByName(column = "Lon")
    private String lon;

    @Column(name = "STATUT_OUVERT")
    @CsvBindByName(column = "STATUT_OUVERT")
    private Boolean statutOuvert;
}
