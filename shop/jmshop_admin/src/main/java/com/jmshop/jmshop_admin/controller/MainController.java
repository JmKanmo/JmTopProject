package com.jmshop.jmshop_admin.controller;

import com.jmshop.jmshop_admin.util.JmUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller("/")
public class MainController {
    @GetMapping
    public String main(Model model) {
        model.addAttribute("product", JmUtil.ofEmpty());
        model.addAttribute("seller", JmUtil.ofEmpty());
        model.addAttribute("coupon", JmUtil.ofEmpty());
        model.addAttribute("category", JmUtil.ofEmpty());
        return "main";
    }
}
