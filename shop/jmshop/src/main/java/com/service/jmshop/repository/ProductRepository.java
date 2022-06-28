package com.service.jmshop.repository;

import com.service.jmshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByOrderByCreatedDateDesc(Pageable pageable);

    List<Product> findProductByCategoryId(Long categoryId);

    List<Product> findProductByNameContainingIgnoreCase(String keyword);
}
