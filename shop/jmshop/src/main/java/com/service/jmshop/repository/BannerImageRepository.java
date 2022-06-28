package com.service.jmshop.repository;

import com.service.jmshop.domain.BannerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {
    List<BannerImage> findBannerImageByOrderByExpirationDateAsc();
}
