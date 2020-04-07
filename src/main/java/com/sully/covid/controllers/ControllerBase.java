package com.sully.covid.controllers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sully.covid.dal.model.ModelBase;
import com.sully.covid.dal.service.ServiceBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public abstract class ControllerBase<ENTITY extends ModelBase, REPOSITORY extends JpaRepository<ENTITY, Long>> {
    protected final String editTemplate;
    protected final String listTemplate;
    protected final String attributeName;
    protected final String path;
    protected final Class<ENTITY> type;
    protected ServiceBase<REPOSITORY, ENTITY> service;

    protected ControllerBase(Class<ENTITY> type, String editTemplate, String listTemplate, String attributeName, String path) {
        this.type = type;
        this.editTemplate = editTemplate;
        this.listTemplate = listTemplate;
        this.attributeName = attributeName;
        this.path = path;
    }

    public String newOne(Model model) {
        model.addAttribute("active", path);
        return editTemplate;
    }

    public String viewOne(Model model, @PathVariable long id) {
        ENTITY entity = this.service.get(id);
        model.addAttribute(attributeName, entity);
        model.addAttribute("active", path);
        return editTemplate;
    }


    public String search(Model model, int pageNumber, String sort, String dir, String keyword, String success) {
        Page<ENTITY> page = this.service.search(PageRequest.of(pageNumber, 20, Sort.Direction.fromString(dir), sort), keyword);

        long firstIndex = page.getSize() * page.getNumber() + 1;
        long lastIndex = page.isLast() ? page.getTotalElements() : firstIndex + page.getSize() - 1;
        model.addAttribute("firstIndex", firstIndex);
        model.addAttribute("lastIndex", lastIndex);
        model.addAttribute("page", page);
        model.addAttribute(attributeName + "Liste", page.getContent());
        model.addAttribute("active", path);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previousPage", page.previousOrFirstPageable().getPageNumber());
        model.addAttribute("nextPage", page.nextOrLastPageable().getPageNumber());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("lastPage", page.getTotalPages() - 1);
        model.addAttribute("success", success);
        return listTemplate;
    }


    public String save(Model model, @ModelAttribute ENTITY entity) {
        try {
            this.service.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/" + path + "/" + entity.getId();
    }

    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        try {
            this.service.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("success", true);
        return new RedirectView("/" + path);
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
                        .withType(type)
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
        return new RedirectView("/" + path);
    }

}
