package com.service.jmshop.utils.banner.repository;

import com.service.jmshop.utils.banner.domain.BannerImage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {
    List<BannerImage> findBannerImagesBy(Sort sort);
}
