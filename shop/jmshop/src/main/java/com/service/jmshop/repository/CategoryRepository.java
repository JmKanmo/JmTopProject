package com.service.jmshop.repository;

import com.service.jmshop.dto.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
