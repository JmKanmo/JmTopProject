package com.service.jmshop.service;

import com.service.jmshop.domain.Product;
import com.service.jmshop.dto.product.ProductMainDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> findTopProduct(Pageable pageable);

    List<ProductMainDto> findProductByCategoryId(Long categoryId, Pageable pageable);

    List<Product> findProductByKeyword(String keyword, Pageable pageable);
}
