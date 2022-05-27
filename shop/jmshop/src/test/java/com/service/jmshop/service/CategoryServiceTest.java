package com.service.jmshop.service;

import com.service.jmshop.dto.domain.Category;
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
    void findCategoryTest() {
        List<Category> categories = categoryService.findCategory();
        assertNotNull(categories);
        assertNotEquals(categories.size(), 0);
    }
}