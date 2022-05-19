package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.Product;
import com.jmshop.jmshop_admin.repository.ProductRepository;
import com.jmshop.jmshop_admin.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
