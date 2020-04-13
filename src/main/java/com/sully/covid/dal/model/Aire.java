package com.sully.covid.dal.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    /**
     * L'aire est-elle gérée par un concessionnaire ?
     * True : concédé
     * False : non concédé
     */
    @Column(name = "CONCESSION")
    @CsvBindByName(column = "CONCESSION")
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
    @CsvBindByName(column = "STATUT_OUVERT")
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
    @CsvBindByName(column = "EQ_PLACESPL")
    private Boolean eqPlacesPl;

    /**
     * L'aire est-elle équipée de sanitaires ?
     */
    @Column(name = "EQ_SANITAIRES")
    @CsvBindByName(column = "EQ_SANITAIRES")
    private Boolean eqSanitaires;

    /**
     * L'aire est-elle équipée de douches
     */
    @Column(name = "EQ_DOUCHES")
    @CsvBindByName(column = "EQ_DOUCHES")
    private Boolean eqDouches;

    /**
     * L'aire est-elle équipée d'une boutique ou d'un
     * service de restauration rapide / à emporter
     */
    @Column(name = "EQ_RESTAU")
    @CsvBindByName(column = "EQ_RESTAU")
    private Boolean eqRestau;

    /**
     * L'aire est-elle équipée de pompes de distribution de carburant poids-lourd ?
     */
    @Column(name = "EQ_CARBU_PL")
    @CsvBindByName(column = "EQ_CARBU_PL")
    private Boolean eqCarbuPl;

    /**
     * Les sanitaires sont-ils en service actuellement ?
     */
    @Column(name = "SERV_SANITAIRES")
    @CsvBindByName(column = "SERV_SANITAIRES")
    private Boolean servSanitaires;

    /**
     * Les douches sont-elles en service actuellement ?
     */
    @Column(name = "SERV_DOUCHES")
    @CsvBindByName(column = "SERV_DOUCHES")
    private Boolean servDouches;

    /**
     * La boutique ou le service de restauration rapide/à emporter est-il en service actuellement ?
     */
    @Column(name = "SERV_RESTAU")
    @CsvBindByName(column = "SERV_RESTAU")
    private Boolean servRestau;

    /**
     * Les pompes de distribution de carburant poids-lourd sont-elles en service actuellement ?
     * "Oui" OU "Non" OU "-" (contraint), Si EQ_CARBU_PL est sur "Non" mettre "Non"
     */
    @Column(name = "SERV_CARBU_PL")
    @CsvBindByName(column = "SERV_CARBU_PL")
    private Boolean servCarbuPl;

    /**
     * Commentaires
     */
    @Column(name = "COM")
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

}
