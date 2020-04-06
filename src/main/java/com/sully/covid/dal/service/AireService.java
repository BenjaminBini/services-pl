package com.sully.covid.dal.service;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.repository.AireRepository;
import com.sully.covid.util.Lambert;
import com.sully.covid.util.LambertPoint;
import com.sully.covid.util.LambertZone;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class AireService {
    private final AireRepository aireRepository;

    public AireService(AireRepository aireRepository) {
        this.aireRepository = aireRepository;
    }

    public Page<Aire> getAll(Pageable page) {
        return this.aireRepository.findAll(page);
    }

    public Page<Aire> search(Pageable pageable, String keyword) {
        return this.aireRepository.findByNomAireContaining(keyword, pageable);
    }

    public List<Aire> getAll() {
        return this.aireRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Aire save(Aire aire) {
        return this.aireRepository.save(aire);
    }

    public Aire get(long id) {
        return this.aireRepository.getOne(id);
    }

    public void delete(long id) {
        this.aireRepository.deleteById(id);
    }


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



    public String toCSV() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        StringWriter writer = new StringWriter();
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withSeparator(';').build();
        beanToCsv.write(this.getAll());
        writer.close();
        return writer.toString();
    }
}
