package com.sully.covid.controllers;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.service.AireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private AireService aireService;

    @Autowired
    public HomeController() {

    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("active", "dashboard");
        List<Aire> aires = this.aireService.getAll();
        long openCount = aires.stream().filter(a -> a.isStatutOuvert()).count();
        float openRatio = (float) openCount / aires.size() * 100;
        model.addAttribute("airesCount", aires.size());
        model.addAttribute("airesOuvertesCount", openCount);
        model.addAttribute("airesOuvertesRatio", Math.round(openRatio * 100.0) / 100.0);
        return "index.jade";
    }


}
