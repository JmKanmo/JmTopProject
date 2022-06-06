package com.service.jmshop.controller.rest;

import com.service.jmshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @BeforeEach
    public void isExist() {
        assertNotNull(categoryController);
    }

    @Test
    void getCategories() {

    }
}