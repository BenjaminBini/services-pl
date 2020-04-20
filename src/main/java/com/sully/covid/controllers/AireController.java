package com.sully.covid.controllers;

import com.sully.covid.configuration.UserPrincipal;
import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.User;
import com.sully.covid.dal.repository.AireRepository;
import com.sully.covid.dal.service.AireService;
import com.sully.covid.dal.service.PublicFormRequestService;
import com.sully.covid.util.Entry;
import com.sully.covid.util.RequestsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class AireController extends ControllerBase<Aire, AireRepository> {

    private final PublicFormRequestService publicFormRequestService;

    @Autowired
    public AireController(AireService aireService, PublicFormRequestService publicFormRequestService) {
        super(Aire.class,
                "aires/aire",
                "aires/aires",
                "aire",
                "aire",
                List.of(new Entry("id", "id"), new Entry("nomAire", "nom"), new Entry("statutOuvert", "statut")),
                List.of(new Entry("id", "Id"), new Entry("nomAire", "Nom"), new Entry("com", "Commentaire"), new Entry("statutOuvert", "Statut")));
        this.service = aireService;
        this.publicFormRequestService = publicFormRequestService;
    }

    @GetMapping("/aire")
    public String aires(Model model, Authentication authentication,
                        @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "id", required = false) String sort,
                        @RequestParam(defaultValue = "asc", required = false) String dir,
                        @RequestParam(defaultValue = "", required = false) String keyword,
                        @RequestParam(defaultValue = "false", required = false) String success) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        return super.search(model, page, sort, dir, keyword, success,
                user.getGest() != null && user.getGest().length() > 0 ? Arrays.asList(user.getGest().split(",")) : null);
    }

    @Override
    @GetMapping("/aire/new")
    public String newOne(Model model) {
        return super.newOne(model);
    }

    @Override
    @GetMapping("/aire/{id}")
    public String viewOne(Model model, @PathVariable long id, @RequestParam(defaultValue = "false", required = false) String success) {
        String view = super.viewOne(model, id, success);
        Aire aire = (Aire) model.getAttribute("aire");
        RequestsAggregate ag = RequestsAggregate.fromAire(aire);
        model.addAttribute("ag", ag);
        return view;
    }

    @Override
    @PostMapping("/aire")
    public RedirectView save(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Aire aire) {
        return super.save(model, redirectAttributes, aire);
    }

    @Override
    @GetMapping("/aire/{id}/delete")
    public RedirectView delete(@PathVariable long id, RedirectAttributes model) {
        return super.delete(id, model);
    }

    @GetMapping("/aire/{id}/delete-requests")
    public RedirectView deleteRequests(@PathVariable long id, RedirectAttributes model) {
        Aire aire = this.service.get(id);
        if (aire != null) {
            aire.getPublicFormRequests().forEach(r -> this.publicFormRequestService.delete(r.getId()));
        }
        model.addAttribute("success", true);
        return new RedirectView("/" + path + "/" + id);
    }

    @Override
    @PostMapping("/aire/import")
    public RedirectView importCSV(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
        return super.importCSV(file, model);
    }

}
