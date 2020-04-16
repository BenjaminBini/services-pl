package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreRoutier;
import com.sully.covid.dal.repository.CentreRoutierRepository;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CentreRoutierService extends ServiceBase<CentreRoutierRepository, CentreRoutier> {

    @Autowired
    public CentreRoutierService(CentreRoutierRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<CentreRoutier> search(Pageable pageable, String keyword, String filter) {
        return this.repository.findByNomCentreContaining(keyword, pageable);
    }

    @Override
    public FeatureCollection toGeoJSON() {
        FeatureCollection featureCollection = new FeatureCollection();
        for (CentreRoutier cr : this.getAll()) {
            try {
                Feature feature = new Feature();
                GeoJsonObject geometry = new Point(Double.parseDouble(cr.getLat()), Double.parseDouble(cr.getLon()));
                feature.setGeometry(geometry);
                feature.setProperty("ID", cr.getId());
                feature.setProperty("TYPE", cr.getType());
                feature.setProperty("NOM_CENTRE", cr.getNomCentre());
                feature.setProperty("DEPARTEMENT", cr.getDepartement());
                feature.setProperty("VOIE", cr.getVoie());
                feature.setProperty("ADRESSE", cr.getAdresse());
                feature.setProperty("COM", cr.getCom());
                feature.setProperty("Lat", cr.getLat());
                feature.setProperty("Lon", cr.getLon());
                featureCollection.add(feature);
            } catch (Exception ex) {
                System.out.println("Unable to serialize CR to GeoJSON : " + cr.getId());
                ex.printStackTrace();
            }
        }
        return featureCollection;
    }
}