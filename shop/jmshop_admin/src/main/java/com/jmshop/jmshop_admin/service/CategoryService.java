package com.jmshop.jmshop_admin.service;

import com.jmshop.jmshop_admin.dto.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Long saveCategory(Category category);

    List<Category> findCategories();

    Optional<Category> findCategoryById(Long id);
}
