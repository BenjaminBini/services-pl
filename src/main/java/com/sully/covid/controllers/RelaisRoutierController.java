package com.sully.covid.controllers;

import com.sully.covid.dal.model.RelaisRoutier;
import com.sully.covid.dal.repository.RelaisRoutierRepository;
import com.sully.covid.dal.service.RelaisRoutierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RelaisRoutierController extends ControllerBase<RelaisRoutier, RelaisRoutierRepository> {

    @Autowired
    public RelaisRoutierController(RelaisRoutierService relaisRoutierService) {
        super(RelaisRoutier.class, "relais/relais", "relais/relais-liste", "relais", "relais");
        this.service = relaisRoutierService;
    }

    @GetMapping("/relais")
    public String aires(Model model, @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "id", required = false) String sort,
                        @RequestParam(defaultValue = "asc", required = false) String dir,
                        @RequestParam(defaultValue = "", required = false) String keyword,
                        @RequestParam(defaultValue = "false", required = false) String success) {
        return super.search(model, page, sort, dir, keyword, success);
    }

    @Override
    @GetMapping("/relais/new")
    public String newOne(Model model) {
        return super.newOne(model);
    }

    @Override
    @GetMapping("/relais/{id}")
    public String viewOne(Model model, @PathVariable long id) {
        return super.viewOne(model, id);
    }

    @Override
    @PostMapping("/relais")
    public String save(Model model, @ModelAttribute RelaisRoutier relais) {
        return super.save(model, relais);
    }

    @Override
    @GetMapping("/relais/{id}/delete")
    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        return super.delete(id, model);
    }

    @Override
    @PostMapping("/relais/import")
    public RedirectView importCSV(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
        return super.importCSV(file, model);
    }
}
