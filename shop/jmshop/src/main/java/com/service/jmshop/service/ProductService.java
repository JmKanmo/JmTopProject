package com.service.jmshop.service;

import com.service.jmshop.dto.domain.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> findTopProduct(Pageable pageable);

    List<Product> findProductByCategoryId(Long categoryId);

    List<Product> findProductByKeyword(String keyword);
}
