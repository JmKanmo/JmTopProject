package com.service.jmshop.service;

import com.service.jmshop.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findCategory();
}
