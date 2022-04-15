package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.domain.Product;
import com.jmshop.jmshop_admin.repository.ProductRepository;
import com.jmshop.jmshop_admin.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Long saveProduct(Product product) {
        return productRepository.save(product).getId();
    }
}
