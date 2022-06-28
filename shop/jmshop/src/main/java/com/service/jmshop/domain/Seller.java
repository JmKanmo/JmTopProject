package com.service.jmshop.domain;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Seller extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "seller_id")
    private Long id;

    @NotEmpty(message = "회사명이 비어있습니다!")
    @NotBlank(message = "회사명은 공백만 올 수 없습니다!")
    @Size(max = 128, message = "회사명은 최대 128글자 까지 작성 가능합니다.")
    private String cName;

    @NotEmpty(message = "대표자명이 비어있습니다!")
    @NotBlank(message = "대표자명은 공백만 올 수 없습니다!")
    @Size(max = 128, message = "대표자명은 최대 128글자 까지 작성 가능합니다.")
    private String uName;

    @NotEmpty(message = "사업자구분이 비어있습니다!")
    @Size(max = 10, message = "사업자구분은 최대 10글자 까지 작성 가능합니다.")
    private String businessType;

    @NotEmpty(message = "주소가 비어있습니다!")
    @NotBlank(message = "주소는 공백만 올 수 없습니다!")
    @Size(max = 128, message = "주소는 최대 128글자 까지 작성 가능합니다.")
    private String address;

    @NotEmpty(message = "이메일이 비어있습니다!")
    @NotBlank(message = "이메일은 공백만 올 수 없습니다!")
    @Email(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "정상적인 이메일 양식이 아닙니다.")
    @Size(max = 128, message = "이메일은 최대 128글자 까지 작성 가능합니다.")
    private String email;

    @URL
    @Size(max = 128, message = "홈페이지 URL은 최대 128글자 까지 작성 가능합니다.")
    private String homepage;

    @NotEmpty(message = "휴대전화 양식이 비어있습니다!")
    @NotBlank(message = "휴대전화 양식은 공백만 올 수 없습니다!")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "휴대전화 양식에 맞지 않습니다.")
    @Size(max = 20, message = "휴대전화 양식은 최대 20자리까지 작성 가능합니다.")
    private String callNumber;

    @Positive
    @Digits(integer = 10, fraction = 10)
    private Long businessNumber;
}
