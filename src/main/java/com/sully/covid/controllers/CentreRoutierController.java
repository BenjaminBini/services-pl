package com.sully.covid.controllers;

import com.sully.covid.dal.model.CentreRoutier;
import com.sully.covid.dal.repository.CentreRoutierRepository;
import com.sully.covid.dal.service.CentreRoutierService;
import com.sully.covid.util.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class CentreRoutierController extends ControllerBase<CentreRoutier, CentreRoutierRepository> {

    @Autowired
    public CentreRoutierController(CentreRoutierService centreRoutierService) {
        super(CentreRoutier.class,
                "routiers/routier",
                "routiers/routiers",
                "routier",
                "routier",
                List.of(new Entry("id", "id"), new Entry("nomCentre", "nom"), new Entry("statutOuvert", "statut")),
                List.of(new Entry("id", "Id"), new Entry("nomCentre", "Nom"), new Entry("com", "Commentaire"), new Entry("statutOuvert", "Statut")));
        this.service = centreRoutierService;
    }

    @GetMapping("/routier")
    public String aires(Model model, @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "id", required = false) String sort,
                        @RequestParam(defaultValue = "asc", required = false) String dir,
                        @RequestParam(defaultValue = "", required = false) String keyword,
                        @RequestParam(defaultValue = "false", required = false) String success) {
        return super.search(model, page, sort, dir, keyword, success, null);
    }

    @Override
    @GetMapping("/routier/new")
    public String newOne(Model model) {
        return super.newOne(model);
    }

    @Override
    @GetMapping("/routier/{id}")
    public String viewOne(Model model, @PathVariable long id) {
        return super.viewOne(model, id);
    }

    @Override
    @PostMapping("/routier")
    public String save(Model model, @ModelAttribute CentreRoutier centreRoutier) {
        return super.save(model, centreRoutier);
    }

    @Override
    @GetMapping("/routier/{id}/delete")
    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        return super.delete(id, model);
    }

    @Override
    @PostMapping("/routier/import")
    public RedirectView importCSV(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
        return super.importCSV(file, model);
    }
}
