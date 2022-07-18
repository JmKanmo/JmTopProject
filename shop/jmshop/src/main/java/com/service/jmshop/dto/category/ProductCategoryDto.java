package com.service.jmshop.dto.category;


import com.service.jmshop.domain.Category;
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
