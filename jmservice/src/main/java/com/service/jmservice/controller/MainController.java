package com.service.jmservice.controller;

import com.service.jmservice.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {
    private CategoryService categoryService;

    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("categories",categoryService.findCategory());
        return "main";
    }
}
