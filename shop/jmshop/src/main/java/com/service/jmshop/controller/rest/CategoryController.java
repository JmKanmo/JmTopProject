package com.service.jmshop.controller.rest;

import com.service.jmshop.dto.category.MenuCategoryDto;
import com.service.jmshop.dto.category.ProductCategoryDto;
import com.service.jmshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("menu")
    public ResponseEntity<List<MenuCategoryDto>> getMenuCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findMenuCategory());
    }

    @GetMapping("product")
    public ResponseEntity<List<ProductCategoryDto>> getProductCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findProductCategory());
    }
}
