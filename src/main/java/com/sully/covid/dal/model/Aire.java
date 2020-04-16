package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.sully.covid.util.Lambert;
import com.sully.covid.util.LambertPoint;
import com.sully.covid.util.LambertZone;
import com.sully.covid.util.StringToBoolConverter;
import lombok.Data;
import org.geojson.Feature;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity(name = "AIRES")
public class Aire implements ModelBase {

    /**
     * Identifiant unique
     */
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

    /**
     * L'aire est-elle gérée par un concessionnaire ?
     * True : concédé
     * False : non concédé
     */
    @Column(name = "CONCESSION")
    @CsvBindByName(column = "CONCESSION")
    @CsvCustomBindByName(converter = StringToBoolConverter.class)
    private boolean concession;

    /**
     * Identification du gestionnaire
     * Identifiant sous forme d'acronyme (se référer au cadre existant)
     */
    @Column(name = "DIR_SCA")
    @CsvBindByName(column = "DIR_SCA")
    private String dirSca;

    /**
     * L'aire est-elle actuellement ouverte ?
     * True : oui
     * False : non
     */
    @Column(name = "STATUT_OUVERT")
    @CsvCustomBindByName(column = "STATUT_OUVERT", converter = StringToBoolConverter.class)
    private boolean statutOuvert;

    /**
     * Identifiant de la route sur laquelle l'aire est située
     * Format alphanumérique :
     * NXXX ou AXXX
     */
    @Column(name = "ROUTE")
    @CsvBindByName(column = "ROUTE")
    private String route;

    /**
     * Nom attribué à l'aire
     */
    @Column(name = "NOM_AIRE")
    @CsvBindByName(column = "NOM_AIRE")
    private String nomAire;

    /**
     * Département où se trouve l'aire
     * Code INSEE à 2 chiffres
     */
    @Column(name = "DEP")
    @CsvBindByName(column = "DEP")
    private String dep;

    /**
     * L'aire est-elle une aire de service ou une aire de repos
     * "Service" OU "Repos" OU "-" (contraint)
     */
    @Column(name = "TYPE_AIRE")
    @CsvBindByName(column = "TYPE_AIRE")
    private String typeAire;

    /**
     * L'aire est-elle équipée de stationnements pour les poids-lourds ?
     */
    @Column(name = "EQ_PLACESPL")
    @CsvCustomBindByName(column = "EQ_PLACESPL", converter = StringToBoolConverter.class)
    private Boolean eqPlacesPl;

    /**
     * L'aire est-elle équipée de sanitaires ?
     */
    @Column(name = "EQ_SANITAIRES")
    @CsvCustomBindByName(column = "EQ_SANITAIRES", converter = StringToBoolConverter.class)
    private Boolean eqSanitaires;

    /**
     * L'aire est-elle équipée de douches
     */
    @Column(name = "EQ_DOUCHES")
    @CsvCustomBindByName(column = "EQ_DOUCHES", converter = StringToBoolConverter.class)
    private Boolean eqDouches;

    /**
     * L'aire est-elle équipée d'une boutique ou d'un
     * service de restauration rapide / à emporter
     */
    @Column(name = "EQ_RESTAU")
    @CsvCustomBindByName(column = "EQ_RESTAU", converter = StringToBoolConverter.class)
    private Boolean eqRestau;

    /**
     * L'aire est-elle équipée de pompes de distribution de carburant poids-lourd ?
     */
    @Column(name = "EQ_CARBU_PL")
    @CsvCustomBindByName(column = "EQ_CARBU_PL", converter = StringToBoolConverter.class)
    private Boolean eqCarbuPl;

    /**
     * Les sanitaires sont-ils en service actuellement ?
     */
    @Column(name = "SERV_SANITAIRES")
    @CsvCustomBindByName(column = "SERV_SANITAIRES", converter = StringToBoolConverter.class)
    private Boolean servSanitaires;

    /**
     * Les douches sont-elles en service actuellement ?
     */
    @Column(name = "SERV_DOUCHES")
    @CsvCustomBindByName(column = "SERV_DOUCHES", converter = StringToBoolConverter.class)
    private Boolean servDouches;

    /**
     * La boutique ou le service de restauration rapide/à emporter est-il en service actuellement ?
     */
    @Column(name = "SERV_RESTAU")
    @CsvCustomBindByName(column = "SERV_RESTAU", converter = StringToBoolConverter.class)
    private Boolean servRestau;

    /**
     * Les pompes de distribution de carburant poids-lourd sont-elles en service actuellement ?
     * "Oui" OU "Non" OU "-" (contraint), Si EQ_CARBU_PL est sur "Non" mettre "Non"
     */
    @Column(name = "SERV_CARBU_PL")
    @CsvCustomBindByName(column = "SERV_CARBU_PL", converter = StringToBoolConverter.class)
    private Boolean servCarbuPl;

    /**
     * Commentaires
     */
    @Column(name = "COM", length = 1000)
    @CsvBindByName(column = "COM")
    private String com;

    /**
     * Coordonnée de longitude géographique de l'aire au format Lambert 93
     * Décimale séparée par un point :
     * "406707.2
     */
    @Column(name = "X")
    @CsvBindByName(column = "X")
    private String x;

    /**
     * Coordonnée de latitude géographique de l'aire au format Lambert 93
     * Décimale séparée par un point :
     * "6404606.29"
     */
    @Column(name = "Y")
    @CsvBindByName(column = "Y")
    private String y;

    @OneToMany(mappedBy = "aire", fetch = FetchType.LAZY)
    private Collection<PublicFormRequest> publicFormRequests;

    @Override
    public Feature toGeoJSON() throws Exception {
        Feature feature = new Feature();
        LambertPoint lambertPoint = Lambert.convertToWGS84Deg(
                Double.parseDouble(this.getX().replace(',', '.')),
                Double.parseDouble(this.getY().replace(',', '.')),
                LambertZone.Lambert93);
        GeoJsonObject geometry = new Point(lambertPoint.getX(), lambertPoint.getY());
        feature.setGeometry(geometry);
        feature.setProperty("ID", this.getId());
        feature.setProperty("NOM_AIRE", this.getNomAire());
        feature.setProperty("CONCESSION", getStringValue(this.isConcession()));
        feature.setProperty("DIR_SCA", this.getDirSca());
        feature.setProperty("STATUT_OUVERT", getStringValue(this.isStatutOuvert()));
        feature.setProperty("ROUTE", this.getRoute());
        feature.setProperty("DEP", this.getDep());
        feature.setProperty("TYPE_AIRE", this.getTypeAire());
        feature.setProperty("EQ_PLACESPL", getStringValue(this.getEqPlacesPl()));
        feature.setProperty("EQ_SANITAIRES", getStringValue(this.getEqSanitaires()));
        feature.setProperty("EQ_DOUCHES", getStringValue(this.getEqDouches()));
        feature.setProperty("EQ_RESTAU", getStringValue(this.getEqRestau()));
        feature.setProperty("SERV_SANITAIRES", getStringValue(this.getServSanitaires()));
        feature.setProperty("SERV_DOUCHES", getStringValue(this.getServDouches()));
        feature.setProperty("SERV_RESTAU", getStringValue(this.getServRestau()));
        feature.setProperty("COM", this.getCom());
        feature.setProperty("X", this.getX());
        feature.setProperty("Y", this.getY());
        return feature;
    }
}
