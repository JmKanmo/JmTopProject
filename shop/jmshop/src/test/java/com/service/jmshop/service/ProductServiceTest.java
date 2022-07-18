package com.service.jmshop.service;

import com.service.jmshop.category.service.CategoryService;
import com.service.jmshop.product.domain.Product;
import com.service.jmshop.product.domain.ProductImage;
import com.service.jmshop.product.dto.ProductMainDto;
import com.service.jmshop.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    public void productServiceTest() {
        assertNotNull(productService);
    }

    @Test
    @Transactional(readOnly = true)
    public void findTopProductTest() {
        try {
            int start = 0;
            int size = 20;
            List<Product> products = productService.findTopProduct(PageRequest.of(start, size, Sort.by("createdDate").descending()));
            assertNotNull(products);
            assertTrue(products.size() >= 0);

            products.forEach(product -> {
                List<ProductImage> productImages = product.getProductImages();
                assertNotNull(productImages);
                assertTrue(productImages.size() >= 0);
            });
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void findProductByCategoryIdTest() {
        try {
            int start = 0;
            int size = 6;
            List<ProductMainDto> products = productService.findProductByCategoryId(categoryService.findProductCategory().stream().findFirst().get().getId(), PageRequest.of(start, size, Sort.by("createdDate").descending()));
            assertNotNull(products);
            assertTrue(products.size() >= 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void findProductByKeywordTest() {
        try {
            int start = 0;
            int size = 15;
            List<Product> products = productService.findProductByKeyword("덕", PageRequest.of(start, size, Sort.by("createdDate").descending()));
            assertNotNull(products);

            products.forEach(product -> {
                List<ProductImage> productImages = product.getProductImages();
                assertNotNull(productImages);
                assertTrue(productImages.size() >= 0);
            });
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}