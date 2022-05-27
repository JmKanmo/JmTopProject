package com.service.jmshop.dto.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @NotEmpty(message = "카테고리명이 비어있습니다!")
    @NotBlank(message = "카테고리명은 공백만 올 수 없습니다!")
    @Size(max = 50, message = "카테고리명은 최대 50글자 까지 작성 가능합니다.")
    private String name;
}