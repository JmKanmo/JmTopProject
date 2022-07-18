package com.service.jmshop.utils.banner.dto;

import com.service.jmshop.utils.banner.domain.BannerImage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BannerImageDto {
    private final String imgSrc;
    private final String link;

    public static BannerImageDto fromEntity(BannerImage bannerImage) {
        return BannerImageDto.builder()
                .imgSrc(bannerImage.getUuid())
                .link(bannerImage.getBannerLink()).build();
    }
}
