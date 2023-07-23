package com.rale.bonkerlist.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ThymeleafNavigation {
    @GetMapping("/")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("/home-app")
    public String home(Model model) {
        return "index";
    }
    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }
    @GetMapping("/showError")
    public String showError(Exception e, Model model) {
        String error = e.getMessage();
        model.addAttribute("message", error);
        return "home-app";
    }
}




































