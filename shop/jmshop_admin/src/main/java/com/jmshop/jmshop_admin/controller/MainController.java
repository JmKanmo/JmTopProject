package com.jmshop.jmshop_admin.controller;

import com.jmshop.jmshop_admin.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {
    @GetMapping(value = {"register", ""})
    public String register(Model model) {
        model.addAttribute("product", Util.ofEmpty());
        model.addAttribute("banner", Util.ofEmpty());
        model.addAttribute("seller", Util.ofEmpty());
        model.addAttribute("coupon", Util.ofEmpty());
        model.addAttribute("category", Util.ofEmpty());
        return "contents/register";
    }

    @GetMapping("update")
    public String update(Model model) {
        return "contents/update";
    }

    @GetMapping("delete")
    public String delete(Model model) {
        return "contents/delete";
    }

    @GetMapping("monitoring")
    public String monitoring(Model model) {
        return "contents/monitoring";
    }
}
