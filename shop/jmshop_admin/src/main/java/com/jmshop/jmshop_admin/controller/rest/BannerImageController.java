package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.BannerImage;
import com.jmshop.jmshop_admin.service.BannerImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class BannerImageController {
    private final BannerImageService bannerImageService;

    @PostMapping(path = "/bannerImage")
    public ResponseEntity<Long> registerBannerImage(@Valid BannerImage bannerImage,
                                                    @RequestParam Map<String, String> paramMap,
                                                    @RequestParam("banner_expiration_date") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date expirationDate) {
        String imgSrc = paramMap.get("imgSrc");
        String bannerLink = paramMap.get("bannerLink");

        if(bannerLink == null || bannerLink.isEmpty()) {
            bannerLink = "/";
        }

        if (imgSrc == null || imgSrc.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1L);
        }

        bannerImage.setExpirationDate(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()));
        bannerImage.setUuid(imgSrc);
        bannerImage.setBannerLink(bannerLink);
        return ResponseEntity.status(HttpStatus.OK).body(bannerImageService.insertBannerImage(bannerImage));
    }
}
