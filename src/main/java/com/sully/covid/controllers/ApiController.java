package com.sully.covid.controllers;


import com.sully.covid.dal.service.AireService;
import com.sully.covid.dal.service.CentreCTService;
import com.sully.covid.dal.service.CentreRoutierService;
import com.sully.covid.dal.service.RelaisRoutierService;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private AireService aireService;

    @Autowired
    private CentreCTService centreCTService;

    @Autowired
    private CentreRoutierService centreRoutierService;

    @Autowired
    private RelaisRoutierService relaisRoutierService;

    @GetMapping(value = "aire/geojson")
    public FeatureCollection airesToGeoJSON() {
        return this.aireService.toGeoJSON();
    }

    @GetMapping(value = "{type}/csv",
            produces = "text/csv")
    public String csvExport(HttpServletResponse response, @PathVariable String type) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + type + ".csv");
            switch (type) {
                case "aire":
                    return this.aireService.toCSV();
                case "ct":
                    return this.centreCTService.toCSV();
                case "routier":
                    return this.centreRoutierService.toCSV();
                case "relais":
                    return this.relaisRoutierService.toCSV();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
