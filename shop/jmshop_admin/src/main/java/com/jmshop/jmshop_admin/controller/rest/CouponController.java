package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Coupon;
import com.jmshop.jmshop_admin.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping(path = "/coupon", consumes = "application/json")
    public ResponseEntity<Long> registerCoupon(@Valid @RequestBody Coupon coupon) {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.saveCoupon(coupon));
    }
}
