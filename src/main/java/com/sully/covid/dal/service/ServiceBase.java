package com.sully.covid.dal.service;


import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sully.covid.dal.model.ModelBase;
import org.apache.commons.lang3.NotImplementedException;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

public abstract  class ServiceBase<REPOSITORY extends JpaRepository<ENTITY, Long>, ENTITY extends ModelBase> {
    protected REPOSITORY repository;

    public Page<ENTITY> search(Pageable pageable, String keyword, String filter) {
        throw new NotImplementedException("Not implemented yet");
    }
        public List<ENTITY> getAll() {
        return this.repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public ENTITY save(ENTITY entity) {
        return this.repository.save(entity);
    }

    public ENTITY get(long id) {
        return this.repository.getOne(id);
    }

    public void delete(long id) {
        this.repository.deleteById(id);
    }

    public String toCSV() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        StringWriter writer = new StringWriter();
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withSeparator(';').build();
        beanToCsv.write(this.getAll());
        writer.close();
        return writer.toString();
    }

    public FeatureCollection toGeoJSON(Boolean filtreStatut) {
        FeatureCollection featureCollection = new FeatureCollection();
        List<ENTITY> entities = this.getAll()
                .stream()
                .filter(a -> filtreStatut == null || filtreStatut.equals(a.isStatutOuvert()))
                .collect(Collectors.toList());
        for (ENTITY entity : entities) {
            try {
                Feature feature = entity.toGeoJSON();
                featureCollection.add(feature);
            } catch (Exception ex) {
                System.out.println("Unable to serialize entity to GeoJSON : " + entity.getId());
                ex.printStackTrace();
            }
        }
        return featureCollection;
    }

}
