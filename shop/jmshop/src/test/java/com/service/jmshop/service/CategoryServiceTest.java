package com.service.jmshop.service;

import com.service.jmshop.category.dto.MenuCategoryDto;
import com.service.jmshop.category.dto.ProductCategoryDto;
import com.service.jmshop.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    public void categoryServiceTest() {
        assertNotNull(categoryService);
    }

    @Test
    void findProductCategoryTest() {
        List<ProductCategoryDto> categories = categoryService.findProductCategory();
        assertNotNull(categories);
        assertTrue(categories.size() >= 0);
    }

    @Test
    void findMenuCategoryTest() {
        List<MenuCategoryDto> categories = categoryService.findMenuCategory();
        assertNotNull(categories);
        assertTrue(categories.size() >= 0);
    }
}