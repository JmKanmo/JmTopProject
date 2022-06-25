package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.ProductImage;
import com.jmshop.jmshop_admin.repository.ProductImageRepository;
import com.jmshop.jmshop_admin.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;

    @Override
    public Long insertProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage).getId();
    }
}
