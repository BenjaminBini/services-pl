package com.sully.covid.controllers;

import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.repository.CentreCTRepository;
import com.sully.covid.dal.service.CentreCTService;
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
public class CentreCTController extends ControllerBase<CentreCT, CentreCTRepository> {

    @Autowired
    public CentreCTController(CentreCTService centreCTService) {
        super(CentreCT.class,
                "ct/ct",
                "ct/cts",
                "ct",
                "ct",
                List.of(new Entry("id", "id"), new Entry("nom", "nom"), new Entry("statutOuvert", "statut")),
                List.of(new Entry("id", "Id"), new Entry("nom", "Nom"), new Entry("statutOuvert", "Statut")));
        this.service = centreCTService;
    }

    @GetMapping("/ct")
    public String list(Model model, @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "id", required = false) String sort,
                        @RequestParam(defaultValue = "asc", required = false) String dir,
                        @RequestParam(defaultValue = "", required = false) String keyword,
                        @RequestParam(defaultValue = "false", required = false) String success) {
        return super.search(model,
                page, sort, dir, keyword, success, null);
    }

    @Override
    @GetMapping("/ct/new")
    public String newOne(Model model) {
        return super.newOne(model);
    }

    @Override
    @GetMapping("/ct/{id}")
    public String viewOne(Model model, @PathVariable long id) {
        return super.viewOne(model, id);
    }

    @Override
    @PostMapping("/ct")
    public String save(Model model, @ModelAttribute CentreCT ct) {
        return super.save(model, ct);
    }

    @Override
    @GetMapping("/ct/{id}/delete")
    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        return super.delete(id, model);
    }

    @Override
    @PostMapping("/ct/import")
    public RedirectView importCSV(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
        return super.importCSV(file, model);
    }
}
