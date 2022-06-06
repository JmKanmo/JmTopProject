package com.jmshop.jmshop_admin.controller;

import com.jmshop.jmshop_admin.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {
    @GetMapping
    public String main(Model model) {
        model.addAttribute("product", Util.ofEmpty());
        model.addAttribute("seller", Util.ofEmpty());
        model.addAttribute("coupon", Util.ofEmpty());
        model.addAttribute("category", Util.ofEmpty());
        return "main";
    }
}
