package com.service.jmshop.category.dto;


import com.service.jmshop.category.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuCategoryDto {
    private final Long id;
    private final String name;

    public static MenuCategoryDto fromEntity(Category category) {
        return MenuCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
