package com.sully.covid.controllers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.service.AireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Controller
public class AireController {

    private final AireService aireService;

    @Autowired
    public AireController(AireService aireService) {
        this.aireService = aireService;

    }

    @GetMapping("/aires")
    public String aires(Model model, @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "id", required = false) String sort,
                        @RequestParam(defaultValue = "asc", required = false) String dir,
                        @RequestParam(defaultValue = "", required = false) String keyword,
                        @RequestParam(defaultValue = "false", required = false) String success) {

        Page<Aire> aires = this.aireService.search(PageRequest.of(page, 50, Sort.Direction.fromString(dir), sort), keyword);

        long firstIndex = aires.getSize() * aires.getNumber() + 1;
        long lastIndex = aires.isLast() ? aires.getTotalElements() : firstIndex + aires.getSize() - 1;
        model.addAttribute("firstIndex", firstIndex);
        model.addAttribute("lastIndex", lastIndex);
        model.addAttribute("page", aires);
        model.addAttribute("aires", aires.getContent());
        model.addAttribute("active", "aires");
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previousPage", aires.previousOrFirstPageable().getPageNumber());
        model.addAttribute("nextPage", aires.nextOrLastPageable().getPageNumber());
        model.addAttribute("currentPage", aires.getNumber());
        model.addAttribute("lastPage", aires.getTotalPages() - 1);
        model.addAttribute("success", success);
        return "aires";
    }

    @GetMapping("/aire")
    public String aire(Model model) {
        return "aire";
    }

    @GetMapping("/aire/{id}")
    public String aire(Model model, @PathVariable long id) {
        Aire aire = this.aireService.get(id);
        model.addAttribute("aire", aire);
        return "aire";
    }

    @PostMapping("/aire")
    public String aire(Model model, @ModelAttribute Aire aire) {
        try {
            this.aireService.save(aire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("aire", aire);
        return "redirect:aire/" + aire.getId();
    }

    @GetMapping("/aire/{id}/delete")
    public RedirectView aire(@PathVariable long id, RedirectAttributes model) {
        try {
            this.aireService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("success", true);
        return new RedirectView("/aires");
    }


    @PostMapping("/aires/import")
    public RedirectView importAires(@RequestParam("file") MultipartFile file, RedirectAttributes model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("importMessage", "Pas de fichier");
            model.addAttribute("success", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Aire.class)
                        .withSeparator(';')
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<Aire> aires = csvToBean.parse();

                for (Aire aire : aires) {
                    try {
                        this.aireService.save(aire);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                // save users list on model
                model.addAttribute("success", true);

            } catch (Exception ex) {
                model.addAttribute("importMessage", "Erreur lors de l'import");
                model.addAttribute("success", false);
            }
        }

        return new RedirectView("/aires");
    }

}
