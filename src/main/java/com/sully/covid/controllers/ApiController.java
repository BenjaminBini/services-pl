package com.sully.covid.controllers;


import com.sully.covid.dal.service.AireService;
import com.sully.covid.dal.service.CentreCTService;
import com.sully.covid.dal.service.CentreRoutierService;
import com.sully.covid.dal.service.RelaisRoutierService;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "{type}/geojson")
    public FeatureCollection airesToGeoJSON(@PathVariable String type,
                                            @RequestParam(defaultValue = "", required = false) String open) {
        Boolean returnOnlyOpenEntities = open.equals("") ? null : open.equals("true");
        switch (type) {
            case "aire":
                return this.aireService.toGeoJSON(returnOnlyOpenEntities);
            case "ct":
                return this.centreCTService.toGeoJSON(returnOnlyOpenEntities);
            case "routier":
                return this.centreRoutierService.toGeoJSON(returnOnlyOpenEntities);
            case "relais":
                return this.relaisRoutierService.toGeoJSON(returnOnlyOpenEntities);
        }
        return null;
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
