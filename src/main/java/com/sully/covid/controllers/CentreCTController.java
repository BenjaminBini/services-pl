package com.sully.covid.controllers;

import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.repository.CentreCTRepository;
import com.sully.covid.dal.service.CentreCTService;
import com.sully.covid.dal.service.PublicFormRequestService;
import com.sully.covid.util.Entry;
import com.sully.covid.util.RequestsAggregate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CentreCTController extends ControllerBase<CentreCT, CentreCTRepository> {

    private CentreCTService centreCTService;
    private final PublicFormRequestService publicFormRequestService;

    @Autowired
    public CentreCTController(PublicFormRequestService publicFormRequestService, CentreCTService centreCTService) {
        super(CentreCT.class,
                "ct/ct",
                "ct/cts",
                "ct",
                "ct",
                List.of(new Entry("id", "id"), new Entry("nom", "nom"), new Entry("commune", "Commune"), new Entry("statutOuvert", "statut")),
                List.of(new Entry("id", "Id"), new Entry("nom", "Nom"), new Entry("commune", "Commune"), new Entry("statutOuvert", "Statut")));
        this.publicFormRequestService = publicFormRequestService;
        this.service = centreCTService;
        this.centreCTService = centreCTService;
    }

    @GetMapping("/ct")
    public String list(Model model, @RequestParam(defaultValue = "0", required = false) int page,
                       @RequestParam(defaultValue = "id", required = false) String sort,
                       @RequestParam(defaultValue = "asc", required = false) String dir,
                       @RequestParam(defaultValue = "", required = false) String keyword,
                       @RequestParam(defaultValue = "false", required = false) String success,
                       @RequestParam(defaultValue = "", required = false) String idsNouveauxCentres) {
        if (idsNouveauxCentres != null && idsNouveauxCentres.length() > 0) {
            model.addAttribute("idsNouveauxCentres", idsNouveauxCentres.split(","));
        }
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
    public String viewOne(Model model, @PathVariable long id, @RequestParam(defaultValue = "false", required = false) String success) {
        String view = super.viewOne(model, id, success);
        CentreCT ct = (CentreCT) model.getAttribute("ct");
        RequestsAggregate ag = RequestsAggregate.fromCT(ct);
        model.addAttribute("ag", ag);
        return view;
    }

    @Override
    @PostMapping("/ct")
    public RedirectView save(Model model, RedirectAttributes redirectAttributes, @ModelAttribute CentreCT ct) {
        return super.save(model, redirectAttributes, ct);
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

    @PostMapping("/ct/partial-import")
    public RedirectView partialImport(@RequestParam("file") MultipartFile file, RedirectAttributes model) {
        if (file.isEmpty()) {
            model.addAttribute("importMessage", "Pas de fichier");
            model.addAttribute("success", false);
        } else {
            try (Workbook workbook = new HSSFWorkbook(file.getInputStream())) {
                model.addAttribute("success", true);
                Sheet sheet = workbook.getSheetAt(1);

                List<CentreCT> nouveauxCentres = new ArrayList<>();
                int i = 0;
                for (Row row : sheet) {
                    i++;
                    if (i >= 4) {
                        try {
                            boolean alreadyExists = true;
                            String codeAgrem = row.getCell(0).getStringCellValue();
                            CentreCT centreCT = this.centreCTService.findByCodeAgrem(codeAgrem);
                            if (centreCT == null) {
                                alreadyExists = false;
                                centreCT = new CentreCT();
                                centreCT.setCodeAgrem(codeAgrem);
                            }
                            String nom = row.getCell(3).toString();
                            String dep = row.getCell(1).toString();
                            String addressLine1 = row.getCell(4).toString();
                            String addressLine2 = row.getCell(5).toString();
                            String zipCode = row.getCell(6).toString();
                            String city = row.getCell(7).toString();
                            double nbCtrl = 0;
                            if (row.getCell(9).getCellTypeEnum() == CellType.NUMERIC) {
                                nbCtrl = row.getCell(9).getNumericCellValue();
                            }
                            centreCT.setNom(nom);
                            centreCT.setDep(dep.split("\\.")[0]);
                            centreCT.setAdresseLigne1(addressLine1);
                            centreCT.setAdresseLigne2(addressLine2);
                            centreCT.setCodeInsee(zipCode.split("\\.")[0]);
                            centreCT.setCommune(city);
                            if (alreadyExists) {
                                centreCT.setStatutOuvert(nbCtrl > 0);
                            } else {
                                centreCT.setStatutOuvert(true);
                                nouveauxCentres.add(centreCT);
                            }
                            this.service.save(centreCT);
                        } catch (Exception ex) {
                            System.out.println("Impossible to import line " + i);
                        }
                    }
                }
                String idsNouveauxCentres = nouveauxCentres.stream()
                        .map(CentreCT::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                model.addAttribute("idsNouveauxCentres", idsNouveauxCentres);

            } catch (Exception e) {
                model.addAttribute("importMessage", "Erreur lors de l'import");
                model.addAttribute("success", false);
            }
        }
        return new RedirectView("/" + path);
    }

    @GetMapping("/ct/{id}/delete-requests")
    public RedirectView deleteRequests(@PathVariable long id, RedirectAttributes model) {
        CentreCT ct = this.service.get(id);
        if (ct != null) {
            ct.getPublicFormRequests().forEach(r -> this.publicFormRequestService.delete(r.getId()));
        }
        model.addAttribute("success", true);
        return new RedirectView("/" + path + "/" + id);
    }
}

