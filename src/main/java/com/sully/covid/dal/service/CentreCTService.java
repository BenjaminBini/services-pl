package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.repository.CentreCTRepository;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CentreCTService extends ServiceBase<CentreCTRepository, CentreCT> {

    @Autowired
    public CentreCTService(CentreCTRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<CentreCT> search(Pageable pageable, String keyword, String filter) {
        return this.repository.findByNomContaining(keyword, pageable);
    }

    @Override
    public FeatureCollection toGeoJSON() {
        FeatureCollection featureCollection = new FeatureCollection();
        for (CentreCT ct : this.getAll()) {
            try {
                Feature feature = new Feature();
                GeoJsonObject geometry = new Point(Double.parseDouble(ct.getLat()), Double.parseDouble(ct.getLon()));
                feature.setGeometry(geometry);
                feature.setProperty("ID", ct.getId());
                feature.setProperty("NOM", ct.getNom());
                feature.setProperty("CODE_AGREM", ct.getCodeAgrem());
                feature.setProperty("STATUT_OUVERT", getStringValue(ct.getStatutOuvert()));
                feature.setProperty("DEP", ct.getDep());
                feature.setProperty("COMMUNE", ct.getCommune());
                feature.setProperty("CODE_INSEE", ct.getCodeInsee());
                feature.setProperty("ADRESSE_L1", ct.getAdresseLigne1());
                feature.setProperty("ADRESSE_L2", ct.getAdresseLigne2());
                feature.setProperty("TEL", ct.getTel());
                feature.setProperty("Lat", ct.getLat());
                feature.setProperty("Lon", ct.getLon());
                featureCollection.add(feature);
            } catch (Exception ex) {
                System.out.println("Unable to serialize CT to GeoJSON : " + ct.getId());
                ex.printStackTrace();
            }
        }
        return featureCollection;
    }
}