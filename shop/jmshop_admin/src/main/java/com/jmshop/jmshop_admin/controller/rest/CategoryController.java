package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Category;
import com.jmshop.jmshop_admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path = "/category")
    public ResponseEntity<Long> registerCategory(@Valid Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.saveCategory(category));
    }

    @GetMapping(path = "/category")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findCategories());
    }
}
