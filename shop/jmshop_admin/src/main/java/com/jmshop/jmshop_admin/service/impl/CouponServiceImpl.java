package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.domain.Coupon;
import com.jmshop.jmshop_admin.repository.CouponRepository;
import com.jmshop.jmshop_admin.service.CouponService;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    private CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Long saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon).getId();
    }
}
