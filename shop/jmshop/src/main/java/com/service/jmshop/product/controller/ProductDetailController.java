package com.service.jmshop.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductDetailController {
    @GetMapping("/product")
    public String getDetailProduct(
            Model model, @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        model.addAttribute("productId", id);
        model.addAttribute("productName", "반갑수다");
        return "contents/product";
    }
}
