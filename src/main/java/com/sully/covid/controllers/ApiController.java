package com.sully.covid.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.sully.covid.dal.service.AireService;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final AireService aireService;

    @Autowired
    public ApiController(AireService aireService) {
        this.aireService = aireService;
    }

    @GetMapping(value = "aires/geojson")
    public FeatureCollection airesToGeoJSON() {
        return this.aireService.toGeoJSON();
    }

    @GetMapping(value = "aires/csv",
            produces = "text/csv")
    public String airesToCSV(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=aires.csv");
            return this.aireService.toCSV();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
