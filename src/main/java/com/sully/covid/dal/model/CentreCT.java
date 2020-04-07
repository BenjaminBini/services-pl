package com.sully.covid.dal.model;


import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "CENTRE_CT")
public class CentreCT implements ModelBase {

    @Id
    @Column(name = "ID")
    @CsvBindByName(column = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NOM")
    @CsvBindByName(column = "NOM")
    private String nom;

    @Column(name = "STATUT_OUVERT")
    @CsvBindByName(column = "STATUT_OUVERT")
    private Boolean statutOuvert;

    @Column(name = "CODE_AGREM")
    @CsvBindByName(column = "CODE_AGREM")
    private String codeAgrem;

    @Column(name = "DEP")
    @CsvBindByName(column = "DEP")
    private String dep;

    @Column(name = "CODE_INSEE")
    @CsvBindByName(column = "CODE_INSEE")
    private String codeInsee;

    @Column(name = "COMMUNE")
    @CsvBindByName(column = "COMMUNE")
    private String commune;

    @Column(name = "ADRESSE_L1")
    @CsvBindByName(column = "ADRESSE_L1")
    private String adresseLigne1;

    @Column(name = "ADRESSE_L2")
    @CsvBindByName(column = "ADRESSE_L2")
    private String adresseLigne2;

    @Column(name = "TEL")
    @CsvBindByName(column = "TEL")
    private String tel;

    @Column(name = "Lat")
    @CsvBindByName(column = "Lat")
    private String lat;

    @Column(name = "Lon")
    @CsvBindByName(column = "Lon")
    private String lon;
}
