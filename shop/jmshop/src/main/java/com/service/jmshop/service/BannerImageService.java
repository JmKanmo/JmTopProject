package com.service.jmshop.service;

import com.service.jmshop.dto.BannerImageDto;

import java.time.LocalDateTime;
import java.util.List;

public interface BannerImageService {
    List<BannerImageDto> findBannerImages(LocalDateTime time);
}
