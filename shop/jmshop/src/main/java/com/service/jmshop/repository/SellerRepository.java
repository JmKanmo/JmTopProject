package com.service.jmshop.repository;

import com.service.jmshop.dto.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
