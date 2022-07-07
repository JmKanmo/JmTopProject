package com.service.jmshop.service.impl;

import com.service.jmshop.dto.BannerImageDto;
import com.service.jmshop.repository.BannerImageRepository;
import com.service.jmshop.service.BannerImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BannerImageServiceImpl implements BannerImageService {
    private final BannerImageRepository bannerImageRepository;

    @Override
    public List<BannerImageDto> findBannerImages(LocalDateTime time) {
        return bannerImageRepository.findBannerImagesBy(Sort.by(Sort.Order.asc("expirationDate")))
                .stream().filter(bannerImage -> bannerImage.getExpirationDate().isAfter(time))
                .map(BannerImageDto::fromEntity).collect(Collectors.toList());
    }
}
