package com.jmshop.jmshop_admin.repository;

import com.jmshop.jmshop_admin.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
