package com.sully.covid.controllers;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.model.CentreRoutier;
import com.sully.covid.dal.model.RelaisRoutier;
import com.sully.covid.dal.service.AireService;
import com.sully.covid.dal.service.CentreCTService;
import com.sully.covid.dal.service.CentreRoutierService;
import com.sully.covid.dal.service.RelaisRoutierService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    private final AireService aireService;

    private final CentreCTService centreCTService;

    private final CentreRoutierService centreRoutierService;

    private final RelaisRoutierService relaisRoutierService;

    @Autowired
    public HomeController(AireService aireService, CentreCTService centreCTService, CentreRoutierService centreRoutierService, RelaisRoutierService relaisRoutierService) {
        this.aireService = aireService;
        this.centreCTService = centreCTService;
        this.centreRoutierService = centreRoutierService;
        this.relaisRoutierService = relaisRoutierService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("active", "dashboard");

        model.addAttribute("stats", new Stats(this.aireService.getAll(),
                this.centreCTService.getAll(),
                this.centreRoutierService.getAll(),
                this.relaisRoutierService.getAll()));
        return "index.jade";
    }

    @Data
    public class Stats {
        private final List<Aire> aires;
        private final List<CentreCT> cts;
        private final List<CentreRoutier> routiers;
        private final List<RelaisRoutier> relais;

        public Stats(List<Aire> aires, List<CentreCT> cts, List<CentreRoutier> routiers, List<RelaisRoutier> relais) {
            this.aires = aires;
            this.cts = cts;
            this.routiers = routiers;
            this.relais = relais;

            airesCount = aires.size();
            airesOuvertesCount = aires.stream().filter(Aire::isStatutOuvert).count();
            airesOuverteRatio = (float) Math.round((float) airesOuvertesCount / airesCount * 10000) / 100;
            placesPlCount = aires.stream().filter(a -> a.getEqPlacesPl() != null && a.getEqPlacesPl()).count();
            placesPlRatio = (float) Math.round((float) placesPlCount / airesCount * 10000) / 100;
            sanitairesCount = aires.stream().filter(a -> a.getEqSanitaires() != null && a.getEqSanitaires()).count();
            sanitairesRatio = (float) Math.round((float) sanitairesCount / airesCount * 10000) / 100;
            sanitairesOuvertsCount = aires.stream().filter(a -> a.getServSanitaires() != null && a.getServSanitaires()).count();
            sanitairesOuvertsRatio = (float) Math.round((float) sanitairesOuvertsCount / sanitairesCount * 10000) / 100;
            douchesCount = aires.stream().filter(a -> a.getEqDouches() != null && a.getEqDouches()).count();
            douchesRatio = (float) Math.round((float) douchesCount / airesCount * 10000) / 100;
            douchesOuvertsCount = aires.stream().filter(a -> a.getServDouches() != null && a.getServDouches()).count();
            douchesOuvertsRatio = (float) Math.round((float) douchesOuvertsCount / douchesCount * 10000) / 100;
            restauCount = aires.stream().filter(a -> a.getEqRestau() != null && a.getEqRestau()).count();
            restauRatio = (float) Math.round((float) restauCount / airesCount * 10000) / 100;
            restauOuvertsCount = aires.stream().filter(a -> a.getServRestau() != null && a.getServRestau()).count();
            restauOuvertsRatio = (float) Math.round((float) restauOuvertsCount / restauCount * 10000) / 100;
            carbuPlCount = aires.stream().filter(a -> a.getEqCarbuPl() != null && a.getEqCarbuPl()).count();
            carbuPlRatio = (float) Math.round((float) carbuPlCount / airesCount * 10000) / 100;
            carbuPlOuvertsCount = aires.stream().filter(a -> a.getServCarbuPl() != null && a.getServCarbuPl()).count();
            carbuPlOuvertsRatio = (float) Math.round((float) carbuPlOuvertsCount / carbuPlCount * 10000) / 100;

            ctCount = cts.size();
            ctOuvertsCount = cts.stream().filter(c -> c.getStatutOuvert() != null && c.getStatutOuvert()).count();
            ctOuvertsRatio = (float) Math.round((float) ctOuvertsCount / ctCount * 10000) / 100;
            routiersCount = routiers.size();
            relaisCount = relais.size();
            relaisOuvertsCount = relais.stream().filter(r -> r.getStatutOuvert() != null && r.getStatutOuvert()).count();
            relaisOuvertsRatio = (float) Math.round((float) relaisOuvertsCount / relaisCount * 10000) / 100;
        }

        private long airesCount;
        private long airesOuvertesCount;
        private float airesOuverteRatio;
        private long placesPlCount;
        private float placesPlRatio;
        private long sanitairesCount;
        private long sanitairesOuvertsCount;
        private float sanitairesOuvertsRatio;
        private long douchesCount;
        private long douchesOuvertsCount;
        private float douchesOuvertsRatio;
        private long restauCount;
        private long restauOuvertsCount;
        private float restauOuvertsRatio;
        private long carbuPlCount;
        private long carbuPlOuvertsCount;
        private float carbuPlOuvertsRatio;
        private final float sanitairesRatio;
        private final float douchesRatio;
        private final float restauRatio;
        private final float carbuPlRatio;

        private long ctCount;
        private long ctOuvertsCount;
        private float ctOuvertsRatio;

        private long routiersCount;
        private long routiersOuvertsCount;
        private float routiersOuvertsRatio;

        private long relaisCount;
        private long relaisOuvertsCount;
        private float relaisOuvertsRatio;
    }

}
