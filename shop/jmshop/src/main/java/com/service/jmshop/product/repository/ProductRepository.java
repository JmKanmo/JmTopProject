package com.service.jmshop.product.repository;

import com.service.jmshop.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductBy(Pageable pageable);

    Page<Product> findProductByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findProductByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
