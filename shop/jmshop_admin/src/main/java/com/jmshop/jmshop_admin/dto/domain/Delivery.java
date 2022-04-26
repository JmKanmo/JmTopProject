package com.jmshop.jmshop_admin.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonProperty("delivery_name")
    @NotEmpty(message = "배달업체명은 비어있습니다!")
    @NotBlank(message = "배달업체명은 공백만 올 수 없습니다!")
    @Size(max = 128, message = "배달업체명은 최대 128글자 까지 작성 가능합니다.")
    private String name;

    @NotEmpty(message = "주소가 비어있습니다!")
    @NotBlank(message = "주소는 공백만 올 수 없습니다!")
    @Size(max = 128, message = "주소는 최대 128글자 까지 작성 가능합니다.")
    private String address;

    @NotEmpty(message = "이메일이 비어있습니다!")
    @NotBlank(message = "이메일은 공백만 올 수 없습니다!")
    @Email
    @Size(max = 128, message = "이메일은 최대 128글자 까지 작성 가능합니다.")
    private String email;

    @URL
    @Size(max = 128, message = "홈페이지 URL은 최대 128글자 까지 작성 가능합니다.")
    private String homepage;

    @JsonProperty("call_number")
    @NotEmpty(message = "연락처가 비어있습니다!")
    @NotBlank(message = "연락처는 공백만 올 수 없습니다!")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "연락처 양식이 맞지 않습니다.")
    @Size(max = 20, message = "연락처는 최대 20자리까지 작성 가능합니다.")
    private String callNumber;

    @JsonProperty("created_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date createdDate;

    @JsonProperty("modified_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date modifiedDate;
}
