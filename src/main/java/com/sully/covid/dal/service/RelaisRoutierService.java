package com.sully.covid.dal.service;

import com.sully.covid.dal.model.RelaisRoutier;
import com.sully.covid.dal.repository.RelaisRoutierRepository;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RelaisRoutierService extends ServiceBase<RelaisRoutierRepository, RelaisRoutier> {

    @Autowired
    public RelaisRoutierService(RelaisRoutierRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<RelaisRoutier> search(Pageable pageable, String keyword, String filter) {
        return this.repository.findByNomContaining(keyword, pageable);
    }

    @Override
    public FeatureCollection toGeoJSON() {
        FeatureCollection featureCollection = new FeatureCollection();
        for (RelaisRoutier relais : this.getAll()) {
            try {
                Feature feature = new Feature();
                GeoJsonObject geometry = new Point(Double.parseDouble(relais.getLat()), Double.parseDouble(relais.getLon()));
                feature.setGeometry(geometry);
                feature.setProperty("ID", relais.getId());
                feature.setProperty("DEP", relais.getDep());
                feature.setProperty("NOM", relais.getNom());
                feature.setProperty("STATUT_OUVERT", getStringValue(relais.getStatutOuvert()));
                feature.setProperty("ADRESSE", relais.getAdresse());
                feature.setProperty("TEL", relais.getTel());
                feature.setProperty("COM", relais.getCom());
                feature.setProperty("LIEN", relais.getLien());
                feature.setProperty("Lat", relais.getLat());
                feature.setProperty("Lon", relais.getLon());
                featureCollection.add(feature);
            } catch (Exception ex) {
                System.out.println("Unable to serialize CT to GeoJSON : " + relais.getId());
                ex.printStackTrace();
            }
        }
        return featureCollection;
    }
}
