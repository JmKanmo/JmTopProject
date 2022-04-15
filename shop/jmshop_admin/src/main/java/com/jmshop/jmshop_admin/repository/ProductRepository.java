package com.jmshop.jmshop_admin.repository;

import com.jmshop.jmshop_admin.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
