package com.jmshop.jmshop_admin.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @JsonProperty("category_name")
    @NotEmpty(message = "카테고리명이 비어있습니다!")
    @NotBlank(message = "카테고리명은 공백만 올 수 없습니다!")
    @Size(max = 50, message = "카테고리명은 최대 50글자 까지 작성 가능합니다.")
    private String name;

    @JsonProperty("created_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date createdDate;

    @JsonProperty("modified_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date modifiedDate;
}
