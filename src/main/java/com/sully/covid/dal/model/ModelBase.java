package com.sully.covid.dal.model;

import org.geojson.Feature;

public interface ModelBase {
    long getId();

    Feature toGeoJSON() throws Exception;

    boolean isStatutOuvert();

    default String getStringValue(Boolean value) {
        if (value == null) {
            return "-";
        } else if (value) {
            return "Oui";
        } else {
            return "Non";
        }
    }
}
