package com.service.jmshop.service;

import com.service.jmshop.dto.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @BeforeEach
    public void productServiceTest() {
        assertNotNull(productService);
    }

    @Test
    @Transactional(readOnly = true)
    public void findTopProduct() {
        try {
            int start = 0;
            int size = 5;
            List<Product> products = productService.findTopProduct(PageRequest.of(start, size));
            assertNotNull(products);
            assertEquals(products.size(), size - start);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void findProductByCategoryIdTest() {
        try {
            List<Product> products = productService.findProductByCategoryId(new Long(1));
            assertNotNull(products);
            assertTrue(products.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void findProductByKeywordTest() {
        try {
            List<Product> products = productService.findProductByKeyword("그린덕");
            assertNotNull(products);
            assertTrue(products.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}