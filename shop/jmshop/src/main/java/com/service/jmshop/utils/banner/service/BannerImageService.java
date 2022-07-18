package com.service.jmshop.utils.banner.service;

import com.service.jmshop.utils.banner.dto.BannerImageDto;

import java.time.LocalDateTime;
import java.util.List;

public interface BannerImageService {
    List<BannerImageDto> findBannerImages(LocalDateTime time);
}
