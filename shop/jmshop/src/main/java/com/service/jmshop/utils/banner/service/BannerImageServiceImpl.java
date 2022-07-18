package com.service.jmshop.utils.banner.service;

import com.service.jmshop.utils.banner.dto.BannerImageDto;
import com.service.jmshop.utils.banner.repository.BannerImageRepository;
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
