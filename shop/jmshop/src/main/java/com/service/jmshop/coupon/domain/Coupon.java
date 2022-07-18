package com.service.jmshop.coupon.domain;


import com.service.jmshop.util.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @NotEmpty(message = "상품명이 비어있습니다!")
    @NotBlank(message = "상품명은 공백만 올 수 없습니다!")
    @Size(max = 128, message = "상품명은 최대 128글자 까지 작성 가능합니다.")
    private String name;

    @Range(min = 0, message = "카테고리는 0 이상의 값만 가능합니다.")
    private Integer categoryId;

    @Size(max = 256, message = "쿠폰설명은 최대 256글자 까지 작성 가능합니다.")
    private String description;

    @Range(min = 0, max = 999999999999L, message = "최소 0원 ~ 999,999,999,999원 범위의 금액만 가능합니다.")
    private Long priceLimit;

    @Range(min = 0, max = Integer.MAX_VALUE, message = "최소 0원 ~ 2,147,483,647원 범위의 금액만 가능합니다.")
    private Integer maxDiscountPrice;

    @Range(min = 0, max = 100, message = "최소 0 ~ 100 범위의 값만 가능합니다.")
    private Integer discountPercent;

    @NotEmpty(message = "유효기간이 비어있습니다!!")
    @NotBlank(message = "유효기간은 공백만 올 수 없습니다!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
}
