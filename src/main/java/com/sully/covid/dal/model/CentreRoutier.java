package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.geojson.Feature;
import org.geojson.GeoJsonObject;
import org.geojson.Point;

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

    @Column(name = "COM", length = 1000)
    @CsvBindByName(column = "COM")
    private String com;

    @Column(name = "Lat")
    @CsvBindByName(column = "lat")
    private String lat;

    @Column(name = "Lon")
    @CsvBindByName(column = "lon")
    private String lon;

    @Override
    public Feature toGeoJSON() {
        Feature feature = new Feature();
        GeoJsonObject geometry = new Point(
                Double.parseDouble(this.getLon().replace(',', '.')),
                Double.parseDouble(this.getLat().replace(',', '.')));
        feature.setGeometry(geometry);
        feature.setProperty("ID", this.getId());
        feature.setProperty("TYPE", this.getType());
        feature.setProperty("NOM_CENTRE", this.getNomCentre());
        feature.setProperty("DEPARTEMENT", this.getDepartement());
        feature.setProperty("VOIE", this.getVoie());
        feature.setProperty("ADRESSE", this.getAdresse());
        feature.setProperty("COM", this.getCom());
        feature.setProperty("Lat", this.getLat());
        feature.setProperty("Lon", this.getLon());
        return feature;
    }

    @Override
    public boolean isStatutOuvert() {
        return true;
    }
}
