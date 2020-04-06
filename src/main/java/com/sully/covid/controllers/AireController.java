package com.sully.covid.controllers;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.repository.AireRepository;
import com.sully.covid.dal.service.AireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AireController extends ControllerBase<Aire, AireRepository> {

    @Autowired
    public AireController(AireService aireService) {
        super("aire", "aires", "aire");
        this.service = aireService;
    }

    @GetMapping("/aires")
    public String aires(Model model, @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "id", required = false) String sort,
                        @RequestParam(defaultValue = "asc", required = false) String dir,
                        @RequestParam(defaultValue = "", required = false) String keyword,
                        @RequestParam(defaultValue = "false", required = false) String success) {
        return super.search(model, page, sort, dir, keyword, success);
    }

    @Override
    @GetMapping("/aire/new")
    public String newOne(Model model) {
        return super.newOne(model);
    }

    @Override
    @GetMapping("/aire/{id}")
    public String viewOne(Model model, @PathVariable long id) {
        return super.viewOne(model, id);
    }

    @Override
    @PostMapping("/aire")
    public String save(Model model, @ModelAttribute Aire aire) {
        return super.save(model, aire);
    }

    @Override
    @GetMapping("/aire/{id}/delete")
    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        return super.delete(id, model);
    }

    @Override
    @PostMapping("/aires/import")
    public RedirectView importCSV(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
        return super.importCSV(file, model);
    }

}
