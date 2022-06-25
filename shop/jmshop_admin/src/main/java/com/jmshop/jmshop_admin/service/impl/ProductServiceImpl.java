package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.Product;
import com.jmshop.jmshop_admin.repository.ProductRepository;
import com.jmshop.jmshop_admin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Long saveProduct(Product product) {
        return productRepository.save(product).getId();
    }
}
