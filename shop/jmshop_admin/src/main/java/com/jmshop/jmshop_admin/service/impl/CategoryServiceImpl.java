package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.domain.Category;
import com.jmshop.jmshop_admin.repository.CategoryRepository;
import com.jmshop.jmshop_admin.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long saveCategory(Category category) {
        return categoryRepository.save(category).getId();
    }
}
