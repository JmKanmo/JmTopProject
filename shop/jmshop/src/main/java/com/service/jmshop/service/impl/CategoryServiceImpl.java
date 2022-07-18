package com.service.jmshop.service.impl;

import com.service.jmshop.dto.category.MenuCategoryDto;
import com.service.jmshop.dto.category.ProductCategoryDto;
import com.service.jmshop.repository.CategoryRepository;
import com.service.jmshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryDto> findProductCategory() {
        return categoryRepository.findAll().stream().map(ProductCategoryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<MenuCategoryDto> findMenuCategory() {
        return categoryRepository.findAll().stream().map(MenuCategoryDto::fromEntity).collect(Collectors.toList());
    }
}
