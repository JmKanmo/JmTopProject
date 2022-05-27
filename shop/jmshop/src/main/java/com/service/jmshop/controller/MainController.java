package com.service.jmshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller("/")
public class MainController {
    @GetMapping
    public String main(Model model) {
        return "main";
    }
}
