package com.service.jmshop.controller.rest;

import com.service.jmshop.dto.CategoryDto;
import com.service.jmshop.service.CategoryService;
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
    void getCategoriesTest() {
        try {
            when(categoryService.findCategory()).thenReturn(Arrays.asList(CategoryDto.builder().build(), CategoryDto.builder().build(), CategoryDto.builder().build()));
            mockMvc.perform(get("/category").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(categoryService, timeout(1)).findCategory();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}