package com.smartcv.builder.controller;

import com.smartcv.builder.service.CVService;
import com.smartcv.builder.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UIController {

    @Autowired
    private CVService cvService;

    @Autowired
    private TemplateService templateService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("cvs", cvService.getUserCVs(userEmail));
        return "dashboard";
    }

    @GetMapping("/cv/new")
    public String createCV(Model model) {
        model.addAttribute("templates", templateService.getAllActiveTemplates());
        return "cv-form";
    }

    @GetMapping("/cv/edit/{id}")
    public String editCV(@PathVariable Long id, Model model) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("cv", cvService.getCVById(id, userEmail));
        model.addAttribute("templates", templateService.getAllActiveTemplates());
        return "cv-form";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        // Admin specific data
        return "admin/dashboard";
    }
}
