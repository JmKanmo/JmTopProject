package com.service.jmshop.category.dto;


import com.service.jmshop.category.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryDto {
    private final Long id;
    private final String name;

    public static ProductCategoryDto fromEntity(Category category) {
        return ProductCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
