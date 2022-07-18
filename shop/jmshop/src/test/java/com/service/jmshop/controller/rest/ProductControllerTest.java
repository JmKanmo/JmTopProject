package com.service.jmshop.controller.rest;

import com.service.jmshop.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            Pageable pageable = PageRequest.of(0, 20, Sort.by("createdDate").descending());

            when(productService.findTopProduct(pageable)).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/product/top")
                            .param("page", "0")
                            .param("size", "20")
                            .accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(productService, timeout(1)).findTopProduct(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getProductByCategoryTest() {
        try {
            when(productService.findProductByCategoryId(anyLong(), any())).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/product/category")
                            .param("categoryId", "0")
                            .param("page", "0")
                            .param("size", "6")
                            .accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(productService, timeout(1)).findProductByCategoryId(anyLong(), any());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getProductByKeywordTest() {
        try {
            when(productService.findProductByKeyword(anyString(), any())).thenReturn(new ArrayList<>());
            mockMvc.perform(get("/product/search")
                            .param("keyword", "")
                            .param("page", "0")
                            .param("size", "15")
                            .accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(productService, timeout(1)).findProductByKeyword(anyString(), any());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}