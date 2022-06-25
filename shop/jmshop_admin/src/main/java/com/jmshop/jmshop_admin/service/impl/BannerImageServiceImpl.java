package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.BannerImage;
import com.jmshop.jmshop_admin.repository.BannerImageRepository;
import com.jmshop.jmshop_admin.service.BannerImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class BannerImageServiceImpl implements BannerImageService {
    private final BannerImageRepository bannerImageRepository;

    @Override
    public Long insertBannerImage(BannerImage bannerImage) {
        return bannerImageRepository.save(bannerImage).getId();
    }
}
