package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.domain.Category;
import com.jmshop.jmshop_admin.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/category", consumes = "application/json")
    public ResponseEntity<Long> registerCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.saveCategory(category));
    }
}
