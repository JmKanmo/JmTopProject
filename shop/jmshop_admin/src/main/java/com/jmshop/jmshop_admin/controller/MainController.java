package com.jmshop.jmshop_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller("/")
public class MainController {
    private static final Optional emptyOptional = Optional.empty();

    @GetMapping
    public String main(Model model) {
        model.addAttribute("product", emptyOptional);
        model.addAttribute("seller", emptyOptional);
        return "main";
    }
}
