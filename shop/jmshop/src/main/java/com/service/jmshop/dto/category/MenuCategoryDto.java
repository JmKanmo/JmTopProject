package com.service.jmshop.dto.category;


import com.service.jmshop.domain.Category;
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
