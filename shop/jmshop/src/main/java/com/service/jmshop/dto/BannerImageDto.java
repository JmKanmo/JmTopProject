package com.service.jmshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BannerImageDto {
    private final String imgSrc;
    private final String link;
}
