package com.sully.covid.controllers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.ModelBase;
import com.sully.covid.dal.service.ServiceBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public abstract class ControllerBase<ENTITY extends ModelBase, REPOSITORY extends JpaRepository<ENTITY, Long>> {
    protected ServiceBase<REPOSITORY, ENTITY> service;
    protected final String editTemplate;
    protected final String listTemplate;
    protected final String attributeName;

    protected ControllerBase(String editTemplate, String listTemplate, String attributeName) {
        this.editTemplate = editTemplate;
        this.listTemplate = listTemplate;
        this.attributeName = attributeName;
    }

    public String newOne(Model model) {
        return editTemplate;
    }

    public String viewOne(Model model, @PathVariable long id) {
        ENTITY entity = this.service.get(id);
        model.addAttribute(attributeName, entity);
        return editTemplate;
    }

    public String save(Model model, @ModelAttribute ENTITY entity) {
        try {
            this.service.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/" + editTemplate + "/" + entity.getId();
    }

    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        try {
            this.service.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("success", true);
        return new RedirectView("/" + listTemplate);
    }

    public RedirectView importCSV(@RequestParam("file") MultipartFile file, RedirectAttributes model) {

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
                List<ENTITY> entities = csvToBean.parse();

                for (ENTITY entity : entities) {
                    try {
                        this.service.save(entity);
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
        return new RedirectView("/" + listTemplate);
    }

}
