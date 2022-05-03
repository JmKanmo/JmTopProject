package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Category;
import com.jmshop.jmshop_admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path = "/category", consumes = "application/json")
    public ResponseEntity<Long> registerCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.saveCategory(category));
    }
}
