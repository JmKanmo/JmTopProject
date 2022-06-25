package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Coupon;
import com.jmshop.jmshop_admin.service.CouponService;
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

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping(path = "/coupon")
    public ResponseEntity<Long> registerCoupon(
            @Valid Coupon coupon,
            @RequestParam("banner_expiration_date") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date expirationDate
    ) {
        coupon.setExpirationDate(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.OK).body(couponService.saveCoupon(coupon));
    }
}
