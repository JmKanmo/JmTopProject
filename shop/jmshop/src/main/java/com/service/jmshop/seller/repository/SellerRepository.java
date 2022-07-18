package com.service.jmshop.seller.repository;

import com.service.jmshop.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
