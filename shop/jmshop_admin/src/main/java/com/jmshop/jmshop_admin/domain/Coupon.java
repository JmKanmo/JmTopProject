package com.jmshop.jmshop_admin.domain;


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
public class Coupon {
    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @JsonProperty("coupon_name")
    @NotEmpty(message = "상품명이 비어있습니다!")
    @NotBlank(message = "상품명은 공백만 올 수 없습니다!")
    @Size(max = 128, message = "상품명은 최대 128글자 까지 작성 가능합니다.")
    private String name;

    @JsonProperty("coupon_description")
    @NotNull(message = "쿠폰 설명은 NULL 이 올 수 없습니다!")
    @Size(max = 256, message = "쿠폰설명은 최대 256글자 까지 작성 가능합니다.")
    private String description;

    @JsonProperty("coupon_limit_price")
    private long limitPrice;

    @JsonProperty("coupon_discount_price")
    private long discountPrice;

    @JsonProperty("coupon_discount")
    private int discount;

    @JsonProperty("created_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date createdDate;

    @JsonProperty("modified_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date modifiedDate;

    @JsonProperty("expired_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date expirationDate;
}
