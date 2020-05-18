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
import java.util.stream.Collectors;

@Controller
public class AireController extends ControllerBase<Aire, AireRepository> {

    private final PublicFormRequestService publicFormRequestService;

    private final List<Entry> codesGest = List.of(
            Entry.of("ADELAC", "ADELAC"),
            Entry.of("ALBEA", "ALBEA"),
            Entry.of("ALIAE", "ALIAE"),
            Entry.of("ALICORNE", "ALICORNE"),
            Entry.of("ALIENOR", "ALIENOR"),
            Entry.of("ALIS", "ALIS"),
            Entry.of("APRR", "APRR"),
            Entry.of("ARCOUR", "ARCOUR"),
            Entry.of("AREA", "AREA"),
            Entry.of("ASF", "ASF"),
            Entry.of("ATLANDES", "ATLANDES"),
            Entry.of("ATMB", "ATMB"),
            Entry.of("COFIROUTE", "COFIROUTE"),
            Entry.of("DIR A", "DIR A"),
            Entry.of("DIR CE", "DIR CE"),
            Entry.of("DIR CO", "DIR CO"),
            Entry.of("DIR Est", "DIR Est"),
            Entry.of("DIR IF", "DIR IF"),
            Entry.of("DIR MC", "DIR MC"),
            Entry.of("DIR MED", "DIR MED"),
            Entry.of("DIR N", "DIR N"),
            Entry.of("DIR NO", "DIR NO"),
            Entry.of("DIR O", "DIR O"),
            Entry.of("DIR SO", "DIR SO"),
            Entry.of("ESCOTA", "ESCOTA"),
            Entry.of("SANEF", "SANEF"),
            Entry.of("SAPN", "SAPN"),
            Entry.of("SFTRF", "SFTRF")
    );

    @Autowired
    public AireController(AireService aireService, PublicFormRequestService publicFormRequestService) {
        super(Aire.class,
                "aires/aire",
                "aires/aires",
                "aire",
                "aire",
                List.of(new Entry("id", "id"), new Entry("nomAire", "nom"), new Entry("typeAire", "type"), new Entry("statutOuvert", "statut")),
                List.of(new Entry("id", "Id"), new Entry("nomAire", "Nom"), new Entry("typeAire", "Type"), new Entry("com", "Commentaire"), new Entry("statutOuvert", "Statut")));
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

        List<String> codesGest = null;
        if (user.getGest() != null && user.getGest().length() > 0) {
            codesGest = Arrays.stream(user.getGest().split(",")).map(String::trim).collect(Collectors.toList());
        }
        return super.search(model, page, sort, dir, keyword, success, codesGest);
    }

    @Override
    @GetMapping("/aire/new")
    public String newOne(Model model) {
        String view = super.newOne(model);
        model.addAttribute("codesGest", codesGest);
        return view;
    }

    @Override
    @GetMapping("/aire/{id}")
    public String viewOne(Model model, @PathVariable long id, @RequestParam(defaultValue = "false", required = false) String success) {
        String view = super.viewOne(model, id, success);
        Aire aire = (Aire) model.getAttribute("aire");
        RequestsAggregate ag = RequestsAggregate.fromAire(aire);
        model.addAttribute("ag", ag);
        model.addAttribute("codesGest", codesGest);
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
