package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.Coupon;
import com.jmshop.jmshop_admin.repository.CouponRepository;
import com.jmshop.jmshop_admin.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    @Override
    public Long saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon).getId();
    }
}
