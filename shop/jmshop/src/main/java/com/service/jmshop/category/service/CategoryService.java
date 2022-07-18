package com.service.jmshop.category.service;

import com.service.jmshop.category.dto.MenuCategoryDto;
import com.service.jmshop.category.dto.ProductCategoryDto;
import java.util.List;

public interface CategoryService {
    List<ProductCategoryDto> findProductCategory();

    List<MenuCategoryDto> findMenuCategory();
}
