package com.service.jmshop.repository;

import com.service.jmshop.domain.Product;
import com.service.jmshop.domain.ProductImage;
import com.service.jmshop.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryRepository;

    @Test
    @Transactional(readOnly = true)
    public void findProductByCategoryIdTest() {
        try {
            int start = 0;
            int size = 6;
            List<Product> products = productRepository.findProductByCategoryId(categoryRepository.findCategory().stream().findFirst().get().getId(),
                    PageRequest.of(start, size, Sort.by("createdDate").descending())).getContent();
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
}
