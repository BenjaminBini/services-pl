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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AireService extends ServiceBase<AireRepository, Aire> {

    @Autowired
    public AireService(AireRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Aire> search(Pageable pageable, String keyword, String filter) {
        Page<Aire> aires = null;
        Sort sort;
        if (filter != null) {
            //aires = this.repository.search(keyword, filter,
            //        pageable.getSort().get().findFirst().get().getProperty(),
            //       pageable.getSort().get().findFirst().get().getDirection() == Sort.Direction.DESC ? "DESC" : "ASC");
        } else {
            String sortProperty = pageable.getSort().get().findFirst().get().getProperty();
            String sortColumn;
            switch (sortProperty) {
                case "nomAire":
                    sortColumn = "NOM_AIRE";
                    break;
                case "statutOuvert":
                    sortColumn = "STATUT_OUVERT";
                    break;
                default:
                    sortColumn = "ID";
            }
            if (pageable.getSort().get().findFirst().get().getDirection() == Sort.Direction.DESC) {
                sort = Sort.by(Sort.Order.desc("requestsCount"), Sort.Order.desc(sortColumn));
            } else {
                sort = Sort.by(Sort.Order.desc("requestsCount"), Sort.Order.asc(sortColumn));
            }
            aires = this.repository.search(keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        }
        return aires;

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
            feature.setProperty("CONCESSION", getStringValue(aire.isConcession()));
            feature.setProperty("DIR_SCA", aire.getDirSca());
            feature.setProperty("STATUT_OUVERT", getStringValue(aire.isStatutOuvert()));
            feature.setProperty("ROUTE", aire.getRoute());
            feature.setProperty("DEP", aire.getDep());
            feature.setProperty("TYPE_AIRE", aire.getTypeAire());
            feature.setProperty("EQ_PLACESPL", getStringValue(aire.getEqPlacesPl()));
            feature.setProperty("EQ_SANITAIRES", getStringValue(aire.getEqSanitaires()));
            feature.setProperty("EQ_DOUCHES", getStringValue(aire.getEqDouches()));
            feature.setProperty("EQ_RESTAU", getStringValue(aire.getEqRestau()));
            feature.setProperty("SERV_SANITAIRES", getStringValue(aire.getServSanitaires()));
            feature.setProperty("SERV_DOUCHES", getStringValue(aire.getServDouches()));
            feature.setProperty("SERV_RESTAU", getStringValue(aire.getServRestau()));
            feature.setProperty("COM", aire.getCom());
            feature.setProperty("X", aire.getX());
            feature.setProperty("Y", aire.getY());
            featureCollection.add(feature);
        }
        return featureCollection;
    }

    public List<String> getRoutes(String dep) {
        return this.getAll().stream()
                .filter(a -> dep == null || a.getDep().equals(dep))
                .map(Aire::getRoute)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getDepartements() {
        return this.getAll().stream()
                .map(Aire::getDep)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
