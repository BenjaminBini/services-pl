package com.sully.covid.controllers;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.model.PublicFormRequest;
import com.sully.covid.dal.model.RelaisRoutier;
import com.sully.covid.dal.service.AireService;
import com.sully.covid.dal.service.CentreCTService;
import com.sully.covid.dal.service.PublicFormRequestService;
import com.sully.covid.dal.service.RelaisRoutierService;
import com.sully.covid.util.Entry;
import com.sully.covid.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PublicFormController {
    private final AireService aireService;
    private final CentreCTService centreCTService;
    private final RelaisRoutierService relaisRoutierService;
    private final PublicFormRequestService publicFormRequestService;

    private final ListUtils listUtils;

    @Autowired
    public PublicFormController(AireService aireService, CentreCTService centreCTService, RelaisRoutierService relaisRoutierService, ListUtils listUtils, PublicFormRequestService publicFormRequestService) {
        this.aireService = aireService;
        this.centreCTService = centreCTService;
        this.relaisRoutierService = relaisRoutierService;
        this.listUtils = listUtils;
        this.publicFormRequestService = publicFormRequestService;
    }

    @GetMapping("/public-form")
    public String publicForm(Model model, @RequestParam(defaultValue = "false", required = false) String success) {
        model.addAttribute("success", success);
        return "public-form";
    }

    @GetMapping("/public-form/relais")
    public String relaisPublicForm(Model model,
                               @RequestParam(required = false) String dep,
                               @RequestParam(required = false) String relaisId,
                               @RequestParam(defaultValue = "false", required = false) String success) {
        if (dep == null && relaisId == null) {
            Map<String, String> codeNomDepartement = listUtils.getDepartements();
            List<Entry> departements = new ArrayList<>();
            this.relaisRoutierService.getDepartements().forEach(
                    d -> {
                        String nomDepartement = codeNomDepartement.getOrDefault(d, d);
                        Entry entry = new Entry(d, nomDepartement);
                        departements.add(entry);
                    }
            );
            departements.sort(Comparator.comparing(Entry::getValue));
            model.addAttribute("departements", departements);
        } else if (dep != null && relaisId == null) {
            model.addAttribute("listeRelais",
                    this.relaisRoutierService.getAll().stream()
                            .filter(a -> a.getDep().equals(dep))
                            .filter(a -> a.getNom() != null && a.getNom().length() > 0)
                            .map(a -> new Entry(String.valueOf(a.getId()), a.getNom()))
                            .sorted(Comparator.comparing(a -> a.getValue().toLowerCase()))
                            .collect(Collectors.toList()));
        } else {
            model.addAttribute("relaisId", relaisId);
            RelaisRoutier relaisRoutier = relaisRoutierService.get(Long.parseLong(relaisId));
            model.addAttribute("relais", relaisRoutier);
        }
        model.addAttribute("dep", dep);
        model.addAttribute("relaisId", relaisId);
        model.addAttribute("success", success);
        return "relais/relais-public-form";
    }

    @PostMapping("/public-form/relais")
    public RedirectView relaisPublicForm(RedirectAttributes model, @ModelAttribute PublicFormRequest publicFormRequest,
                                     @RequestParam(required = false) String dep,
                                     @RequestParam(required = false) String relaisId,
                                     @RequestParam(required = false, defaultValue = "false") String relaisChoisi) {
        if (publicFormRequest.getRelaisId() != null && publicFormRequest.getRelaisId() > 0 && relaisChoisi.equals("true")) {
            publicFormRequestService.save(publicFormRequest);
            model.addAttribute("success", true);
            return new RedirectView("/public-form");
        } else {
            model.addAttribute("dep", dep);
            model.addAttribute("relaisId", relaisId);
        }
        return new RedirectView("/public-form/relais");
    }

    @GetMapping("/public-form/ct")
    public String ctPublicForm(Model model,
                                 @RequestParam(required = false) String dep,
                                 @RequestParam(required = false) String ctId,
                                 @RequestParam(defaultValue = "false", required = false) String success) {
        if (dep == null && ctId == null) {
            Map<String, String> codeNomDepartement = listUtils.getDepartements();
            List<Entry> departements = new ArrayList<>();
            this.centreCTService.getDepartements().forEach(
                    d -> {
                        String nomDepartement = codeNomDepartement.getOrDefault(d, d);
                        Entry entry = new Entry(d, nomDepartement);
                        departements.add(entry);
                    }
            );
            departements.sort(Comparator.comparing(Entry::getValue));
            model.addAttribute("departements", departements);
        } else if (dep != null && ctId == null) {
            model.addAttribute("cts",
                    this.centreCTService.getAll().stream()
                            .filter(a -> a.getDep().equals(dep))
                            .filter(a -> a.getNom() != null && a.getNom().length() > 0)
                            .map(a -> new Entry(String.valueOf(a.getId()), a.getNom() + " - " + a.getCommune()))
                            .sorted(Comparator.comparing(a -> a.getValue().toLowerCase()))
                            .collect(Collectors.toList()));
        } else {
            model.addAttribute("ctId", ctId);
            CentreCT centreCT = centreCTService.get(Long.valueOf(ctId));
            model.addAttribute("ct", centreCT);
        }
        model.addAttribute("dep", dep);
        model.addAttribute("ctId", ctId);
        model.addAttribute("success", success);
        return "ct/ct-public-form";
    }

    @PostMapping("/public-form/ct")
    public RedirectView ctPublicForm(RedirectAttributes model, @ModelAttribute PublicFormRequest publicFormRequest,
                                       @RequestParam(required = false) String dep,
                                       @RequestParam(required = false) String ctId,
                                       @RequestParam(required = false, defaultValue = "false") String ctChoisi) {
        if (publicFormRequest.getCtId() != null && publicFormRequest.getCtId() > 0 && ctChoisi.equals("true")) {
            publicFormRequestService.save(publicFormRequest);
            model.addAttribute("success", true);
            return new RedirectView("/public-form");
        } else {
            model.addAttribute("dep", dep);
            model.addAttribute("ctId", ctId);
        }
        return new RedirectView("/public-form/ct");
    }

    @GetMapping("/public-form/aire")
    public String airePublicForm(Model model,
                             @RequestParam(required = false) String dep,
                             @RequestParam(required = false) String route,
                             @RequestParam(required = false) String aireId,
                             @RequestParam(defaultValue = "false", required = false) String success) {
        if (dep == null && aireId == null) {
            Map<String, String> codeNomDepartement = listUtils.getDepartements();
            List<Entry> departements = new ArrayList<>();
            this.aireService.getDepartements().forEach(
                    d -> {
                        String nomDepartement = codeNomDepartement.getOrDefault(d, d);
                        Entry entry = new Entry(d, nomDepartement);
                        departements.add(entry);
                    }
            );
            departements.sort(Comparator.comparing(Entry::getValue));
            model.addAttribute("departements", departements);
        } else if (dep != null && route == null && aireId == null) {
            model.addAttribute("routes", this.aireService.getRoutes(dep).stream()
                    .map(r -> new Entry(r, r)).collect(Collectors.toList()));
        } else if (aireId == null) {
            model.addAttribute("aires",
                    this.aireService.getAll().stream()
                            .filter(a -> a.getRoute().equals(route))
                            .filter(a -> a.getDep().equals(dep))
                            .filter(a -> a.getNomAire() != null && a.getNomAire().length() > 0)
                            .map(a -> new Entry(String.valueOf(a.getId()), a.getNomAire()))
                            .sorted(Comparator.comparing(a -> a.getValue().toLowerCase()))
                            .collect(Collectors.toList()));
        } else {
            model.addAttribute("aireId", aireId);
            Aire aire = aireService.get(Long.valueOf(aireId));
            model.addAttribute("aire", aire);
        }
        model.addAttribute("dep", dep);
        model.addAttribute("route", route);
        model.addAttribute("aireId", aireId);
        model.addAttribute("success", success);
        return "aires/aire-public-form";
    }

    @PostMapping("/public-form/aire")
    public RedirectView airePublicForm(RedirectAttributes model, @ModelAttribute PublicFormRequest publicFormRequest,
                                   @RequestParam(required = false) String dep,
                                   @RequestParam(required = false) String route,
                                   @RequestParam(required = false) String aireId,
                                   @RequestParam(required = false, defaultValue = "false") String aireChoisie) {
        if (publicFormRequest.getAireId() != null && publicFormRequest.getAireId() > 0 && aireChoisie.equals("true")) {
            publicFormRequestService.save(publicFormRequest);
            model.addAttribute("success", true);
            return new RedirectView("/public-form");
        } else {
            model.addAttribute("dep", dep);
            model.addAttribute("route", route);
            model.addAttribute("aireId", aireId);
        }
        return new RedirectView("/public-form/aire");
    }
}
