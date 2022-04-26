package com.jmshop.jmshop_admin.repository;

import com.jmshop.jmshop_admin.dto.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
