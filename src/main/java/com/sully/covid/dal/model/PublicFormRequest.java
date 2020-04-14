package com.sully.covid.dal.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity(name = "PUBLIC_FORM_REQUEST")
@EntityListeners(AuditingEntityListener.class)
public class PublicFormRequest implements ModelBase {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_AIRE", referencedColumnName = "ID", insertable = false, updatable = false)
    private Aire aire;

    @Column(name = "ID_AIRE")
    private long aireId;

    /**
     * L'aire est-elle actuellement ouverte ?
     * True : oui
     * False : non
     */
    @Column(name = "STATUT_OUVERT", nullable = false)
    private boolean statutOuvert;

    /**
     * L'aire est-elle équipée de stationnements pour les poids-lourds ?
     */
    @Column(name = "EQ_PLACESPL", nullable = false)
    private boolean eqPlacesPl;

    /**
     * L'aire est-elle équipée de sanitaires ?
     */
    @Column(name = "EQ_SANITAIRES", nullable = false)
    private boolean eqSanitaires;

    /**
     * L'aire est-elle équipée de douches
     */
    @Column(name = "EQ_DOUCHES", nullable = false)
    private boolean eqDouches;

    /**
     * L'aire est-elle équipée d'une boutique ou d'un
     * service de restauration rapide / à emporter
     */
    @Column(name = "EQ_RESTAU", nullable = false)
    private boolean eqRestau;

    /**
     * L'aire est-elle équipée de pompes de distribution de carburant poids-lourd ?
     */
    @Column(name = "EQ_CARBU_PL", nullable = false)
    private boolean eqCarbuPl;

    /**
     * Les sanitaires sont-ils en service actuellement ?
     */
    @Column(name = "SERV_SANITAIRES", nullable = false)
    private boolean servSanitaires;

    /**
     * Les douches sont-elles en service actuellement ?
     */
    @Column(name = "SERV_DOUCHES", nullable = false)
    private boolean servDouches;

    /**
     * La boutique ou le service de restauration rapide/à emporter est-il en service actuellement ?
     */
    @Column(name = "SERV_RESTAU", nullable = false)
    private boolean servRestau;

    /**
     * Les pompes de distribution de carburant poids-lourd sont-elles en service actuellement ?
     * "Oui" OU "Non" OU "-" (contraint), Si EQ_CARBU_PL est sur "Non" mettre "Non"
     */
    @Column(name = "SERV_CARBU_PL", nullable = false)
    private boolean servCarbuPl;

    /**
     * Commentaires
     */
    @Column(name = "COM", length = 1000)
    private String com;

    /**
     * Date de création
     */
    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    public String getCommentForDisplay() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String result = dateFormatter.format(this.getCreationDate());
        if (this.getCom() != null && this.getCom().length() > 0) {
            result += " - " + this.getCom();
        }
        return result;
    }
}
