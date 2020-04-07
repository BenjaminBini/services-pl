package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "RELAIS_ROUTIER")
public class RelaisRoutier implements ModelBase {
    @Id
    @Column(name = "ID")
    @CsvBindByName(column = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "Lat")
    @CsvBindByName(column = "Lat")
    String lat;

    @Column(name = "Lon")
    @CsvBindByName(column = "Lon")
    String lon;

    @Column(name = "DEP")
    @CsvBindByName(column = "DEP")
    private String dep;

    @Column(name = "NOM")
    @CsvBindByName(column = "NOM")
    private String nom;

    @Column(name = "STATUT_OUVERT")
    @CsvBindByName(column = "STATUT_OUVERT")
    private Boolean statutOuvert;

    @Column(name = "ADRESSE")
    @CsvBindByName(column = "ADRESSE")
    private String adresse;

    @Column(name = "TEL")
    @CsvBindByName(column = "TEL")
    private String tel;

    @Column(name = "COM")
    @CsvBindByName(column = "COM")
    private String com;
}
