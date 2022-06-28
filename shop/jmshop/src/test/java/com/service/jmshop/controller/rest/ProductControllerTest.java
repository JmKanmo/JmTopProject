package com.service.jmshop.controller.rest;

import com.service.jmshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTopProductTest() {
        try {
            when(productService.findTopProduct(PageRequest.of(0, 20))).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/product/top")
                            .param("page", "0")
                            .param("size", "20").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(productService, timeout(1)).findTopProduct(PageRequest.of(0, 20));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getProductByCategoryTest() {
        try {
            when(productService.findProductByCategoryId(anyLong())).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/product/category")
                            .param("categoryId", "0").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(productService, timeout(1)).findProductByCategoryId(anyLong());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getProductByKeywordTest() {
        try {
            when(productService.findProductByKeyword(anyString())).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/product/search")
                            .param("keyword", "").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(productService, timeout(1)).findProductByKeyword(anyString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}