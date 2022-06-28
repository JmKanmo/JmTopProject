package com.service.jmshop.controller.rest;

import com.service.jmshop.dto.BannerImageDto;
import com.service.jmshop.service.BannerImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/banner-image")
@RequiredArgsConstructor
public class BannerImageController {
    private final BannerImageService bannerImageService;

    @GetMapping
    public ResponseEntity<List<BannerImageDto>> getBannerImages() {
        return ResponseEntity.status(HttpStatus.OK).body(bannerImageService.findBannerImages(LocalDateTime.now()));
    }
}
