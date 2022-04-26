package com.jmshop.jmshop_admin.repository;

import com.jmshop.jmshop_admin.dto.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
