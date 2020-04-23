package com.sully.covid.dal.model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.sully.covid.util.StringToBoolConverter;
import lombok.Data;
import org.geojson.Feature;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity(name = "CENTRE_CT")
public class CentreCT implements ModelBase {

    @Id
    @Column(name = "ID")
    @CsvBindByName(column = "ID")
    @GenericGenerator(
            name = "assigned-identity",
            strategy = "com.sully.covid.dal.AssignedIdentityGenerator"
    )
    @GeneratedValue(
            generator = "assigned-identity",
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @Column(name = "NOM")
    @CsvBindByName(column = "NOM")
    private String nom;

    @Column(name = "STATUT_OUVERT")
    @CsvCustomBindByName(column = "STATUT_OUVERT", converter = StringToBoolConverter.class)
    private boolean statutOuvert;

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

    @OneToMany(mappedBy = "centreCT", fetch = FetchType.LAZY)
    private Collection<PublicFormRequest> publicFormRequests;

    @Override
    public Feature toGeoJSON() {
        Feature feature = new Feature();
        GeoJsonObject geometry = new Point(
                Double.parseDouble(this.getLon().replace(',', '.')),
                Double.parseDouble(this.getLat().replace(',', '.')));
        feature.setGeometry(geometry);
        feature.setProperty("ID", this.getId());
        feature.setProperty("NOM", this.getNom());
        feature.setProperty("CODE_AGREM", this.getCodeAgrem());
        feature.setProperty("STATUT_OUVERT", getStringValue(this.isStatutOuvert()));
        feature.setProperty("DEP", this.getDep());
        feature.setProperty("COMMUNE", this.getCommune());
        feature.setProperty("CODE_INSEE", this.getCodeInsee());
        feature.setProperty("ADRESSE_L1", this.getAdresseLigne1());
        feature.setProperty("ADRESSE_L2", this.getAdresseLigne2());
        feature.setProperty("TEL", this.getTel());
        feature.setProperty("Lat", this.getLat());
        feature.setProperty("Lon", this.getLon());
        return feature;
    }

}
