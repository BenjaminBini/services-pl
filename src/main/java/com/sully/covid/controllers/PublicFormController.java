package com.sully.covid.controllers;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.PublicFormRequest;
import com.sully.covid.dal.service.AireService;
import com.sully.covid.dal.service.PublicFormRequestService;
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
    private final PublicFormRequestService publicFormRequestService;

    private final ListUtils listUtils;

    @Autowired
    public PublicFormController(AireService aireService, ListUtils listUtils, PublicFormRequestService publicFormRequestService) {
        this.aireService = aireService;
        this.listUtils = listUtils;
        this.publicFormRequestService = publicFormRequestService;
    }

    @GetMapping("/public-form")
    public String publicForm(Model model,
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
        return "aires/public-form";
    }

    @PostMapping("/public-form")
    public RedirectView publicForm(RedirectAttributes model, @ModelAttribute PublicFormRequest publicFormRequest,
                                   @RequestParam(required = false) String dep,
                                   @RequestParam(required = false) String route,
                                   @RequestParam(required = false) String aireId,
                                   @RequestParam(required = false, defaultValue = "false") String aireChoisie) {
        if (publicFormRequest.getAireId() > 0 && aireChoisie.equals("true")) {
            publicFormRequestService.save(publicFormRequest);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("dep", dep);
            model.addAttribute("route", route);
            model.addAttribute("aireId", aireId);
        }
        return new RedirectView("/public-form");
    }
}
