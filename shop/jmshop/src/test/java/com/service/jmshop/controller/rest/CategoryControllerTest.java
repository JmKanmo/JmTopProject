package com.service.jmshop.controller.rest;

import com.service.jmshop.category.dto.MenuCategoryDto;
import com.service.jmshop.category.dto.ProductCategoryDto;
import com.service.jmshop.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProductCategoriesTest() {
        try {
            when(categoryService.findProductCategory()).thenReturn(Arrays.asList(ProductCategoryDto.builder().build(), ProductCategoryDto.builder().build(), ProductCategoryDto.builder().build()));
            mockMvc.perform(get("/category/product").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(categoryService, timeout(1)).findProductCategory();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void getMenuCategoriesTest() {
        try {
            when(categoryService.findMenuCategory()).thenReturn(Arrays.asList(MenuCategoryDto.builder().build(), MenuCategoryDto.builder().build(), MenuCategoryDto.builder().build()));
            mockMvc.perform(get("/category/menu").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(categoryService, timeout(1)).findMenuCategory();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}