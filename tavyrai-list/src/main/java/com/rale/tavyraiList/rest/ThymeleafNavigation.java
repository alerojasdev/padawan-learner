package com.rale.tavyraiList.rest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
@RequestMapping("/tavyrai-list")
public class ThymeleafNavigation {
    private int clickCount = 0;

    @GetMapping("/login")
    public String login(Model model) {
        clickCount = 0;
        return "login";
    }

    @GetMapping("/home-app")
    public String home(Model model) {
        model.addAttribute("clickCount", clickCount);
        return "home-app";
    }

    @PostMapping("/home-app")
    public String increment(Model model) {
        clickCount++;
        model.addAttribute("clickCount", clickCount);
        return "home-app";
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
