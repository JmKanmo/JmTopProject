package com.service.jmshop.category.controller;

import com.service.jmshop.category.service.CategoryService;
import com.service.jmshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/categories/{id}")
    public String categories(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);
        return "contents/categories";
    }
}
