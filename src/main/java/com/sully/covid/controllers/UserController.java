package com.sully.covid.controllers;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.model.User;
import com.sully.covid.dal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("active", "users");
        model.addAttribute("users", this.userService.getAll());
        return "users";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable long id) {
        if (!this.userService.get(id).getUsername().equals("admin")) {
            this.userService.delete(id);
        }
        return "redirect:/users";
    }

    @PostMapping("/users")
    public String user(Model model, @ModelAttribute User user) {
        try {
            this.userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/users";
    }

}
