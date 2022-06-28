package com.service.jmshop.service.impl;

import com.service.jmshop.domain.Product;
import com.service.jmshop.repository.ProductRepository;
import com.service.jmshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findTopProduct(Pageable pageable) {
        return productRepository.findProductByOrderByCreatedDateDesc(pageable).getContent();
    }

    @Override
    public List<Product> findProductByCategoryId(Long categoryId) {
        return productRepository.findProductByCategoryId(categoryId);
    }

    @Override
    public List<Product> findProductByKeyword(String keyword) {
        return productRepository.findProductByNameContainingIgnoreCase(keyword);
    }
}
