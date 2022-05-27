package com.service.jmshop.service.impl;

import com.service.jmshop.dto.domain.Category;
import com.service.jmshop.repository.CategoryRepository;
import com.service.jmshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findCategory() {
        return categoryRepository.findAll();
    }
}
