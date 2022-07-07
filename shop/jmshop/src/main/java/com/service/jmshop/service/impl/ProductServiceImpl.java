package com.service.jmshop.service.impl;

import com.service.jmshop.domain.Product;
import com.service.jmshop.dto.ProductMainDto;
import com.service.jmshop.repository.ProductRepository;
import com.service.jmshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findTopProduct(Pageable pageable) {
        return productRepository.findProductBy(pageable).getContent();
    }

    @Override
    public List<ProductMainDto> findProductByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findProductByCategoryId(categoryId, pageable).getContent().stream().map(ProductMainDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<Product> findProductByKeyword(String keyword, Pageable pageable) {
        return productRepository.findProductByNameContainingIgnoreCase(keyword, pageable).getContent();
    }
}
