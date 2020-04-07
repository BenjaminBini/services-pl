package com.sully.covid.dal.service;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.repository.AireRepository;
import com.sully.covid.util.Lambert;
import com.sully.covid.util.LambertPoint;
import com.sully.covid.util.LambertZone;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AireService extends ServiceBase<AireRepository, Aire> {

    @Autowired
    public AireService(AireRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Aire> search(Pageable pageable, String keyword) {
        return this.repository.findByNomAireContaining(keyword, pageable);
    }

    @Override
    public FeatureCollection toGeoJSON() {
        FeatureCollection featureCollection = new FeatureCollection();
        for (Aire aire : this.getAll()) {
            Feature feature = new Feature();
            LambertPoint lambertPoint = Lambert.convertToWGS84Deg(Double.parseDouble(aire.getX()), Double.parseDouble(aire.getY()), LambertZone.Lambert93);
            GeoJsonObject geometry = new Point(lambertPoint.getX(), lambertPoint.getY());
            feature.setGeometry(geometry);
            feature.setProperty("ID", aire.getId());
            feature.setProperty("NOM_AIRE", aire.getNomAire());
            feature.setProperty("CONCESSION", aire.isConcession());
            feature.setProperty("DIR_SCA", aire.getDirSca());
            feature.setProperty("STATUT_OUVERT", aire.isStatutOuvert());
            feature.setProperty("ROUTE", aire.getRoute());
            feature.setProperty("DEP", aire.getDep());
            feature.setProperty("TYPE_AIRE", aire.getTypeAire());
            feature.setProperty("EQ_PLACESPL", aire.getEqPlacesPl());
            feature.setProperty("EQ_SANITAIRES", aire.getEqSanitaires());
            feature.setProperty("EQ_DOUCHES", aire.getEqDouches());
            feature.setProperty("EQ_RESTAU", aire.getEqRestau());
            feature.setProperty("SERV_SANITAIRES", aire.getServSanitaires());
            feature.setProperty("SERV_DOUCHES", aire.getServDouches());
            feature.setProperty("SERV_RESTAU", aire.getServRestau());
            feature.setProperty("COM", aire.getCom());
            feature.setProperty("X", aire.getX());
            feature.setProperty("Y", aire.getY());
            featureCollection.add(feature);
        }
        return featureCollection;
    }
}
