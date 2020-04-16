package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.sully.covid.util.StringToBoolConverter;
import lombok.Data;
import org.geojson.Feature;
import org.geojson.GeoJsonObject;
import org.geojson.Point;

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
    private boolean statutOuvert;

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

    @Override
    public Feature toGeoJSON() {
        Feature feature = new Feature();
        GeoJsonObject geometry = new Point(
                Double.parseDouble(this.getLon().replace(',', '.')),
                Double.parseDouble(this.getLat().replace(',', '.')));
        feature.setGeometry(geometry);
        feature.setProperty("ID", this.getId());
        feature.setProperty("DEP", this.getDep());
        feature.setProperty("NOM", this.getNom());
        feature.setProperty("STATUT_OUVERT", getStringValue(this.isStatutOuvert()));
        feature.setProperty("ADRESSE", this.getAdresse());
        feature.setProperty("TEL", this.getTel());
        feature.setProperty("COM", this.getCom());
        feature.setProperty("LIEN", this.getLien());
        feature.setProperty("Lat", this.getLat());
        feature.setProperty("Lon", this.getLon());
        return feature;
    }

    @Override
    public boolean isStatutOuvert() {
        return this.statutOuvert;
    }
}
