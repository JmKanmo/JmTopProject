package com.service.jmshop.repository;

import com.service.jmshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductBy(Pageable pageable);

    Page<Product> findProductByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findProductByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
