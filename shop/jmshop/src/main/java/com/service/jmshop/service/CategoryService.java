package com.service.jmshop.service;

import com.service.jmshop.dto.category.MenuCategoryDto;
import com.service.jmshop.dto.category.ProductCategoryDto;
import java.util.List;

public interface CategoryService {
    List<ProductCategoryDto> findProductCategory();

    List<MenuCategoryDto> findMenuCategory();
}
