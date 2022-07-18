package com.service.jmshop.service;

import com.service.jmshop.utils.banner.dto.BannerImageDto;
import com.service.jmshop.utils.banner.service.BannerImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BannerImageServiceTest {
    @Autowired
    private BannerImageService bannerImageService;


    @Test
    @DisplayName("현재 시간 기준으로 배너 이미지 반환 테스트")
    public void findBannerImageCurTimeTest() {
        List<BannerImageDto> bannerImages = bannerImageService.findBannerImages(LocalDateTime.now());
        assertNotNull(bannerImages);
        assertEquals(bannerImages.size() >= 0, true);
    }

    @Test
    @DisplayName("훨씬 이후의 시간 기준으로 배너 이미지 반환 테스트")
    public void findBannerImageAfterTimeTest() {
        List<BannerImageDto> bannerImages = bannerImageService.findBannerImages(LocalDateTime.of(350000, 6, 19, 1, 12, 0));
        assertNotNull(bannerImages);
        assertEquals(bannerImages.size() > 0, false);
    }
}
