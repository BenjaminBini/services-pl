package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.sully.covid.util.StringToBoolConverter;
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
    @CsvBindByName(column = "lat")
    String lat;

    @Column(name = "Lon")
    @CsvBindByName(column = "lon")
    String lon;

    @Column(name = "DEP")
    @CsvBindByName(column = "DEP")
    private String dep;

    @Column(name = "NOM")
    @CsvBindByName(column = "NOM")
    private String nom;

    @Column(name = "STATUT_OUVERT")
    @CsvCustomBindByName(column = "STATUT_OUVERT", converter = StringToBoolConverter.class)
    private Boolean statutOuvert;

    @Column(name = "ADRESSE")
    @CsvBindByName(column = "ADRESSE")
    private String adresse;

    @Column(name = "TEL")
    @CsvBindByName(column = "TEL")
    private String tel;

    @Column(name = "LIEN")
    @CsvBindByName(column = "LIEN")
    private String lien;

    @Column(name = "COM", length = 1000)
    @CsvBindByName(column = "COM")
    private String com;
}
