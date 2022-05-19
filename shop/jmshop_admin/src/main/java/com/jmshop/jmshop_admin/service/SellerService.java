package com.jmshop.jmshop_admin.service;

import com.jmshop.jmshop_admin.dto.domain.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Long saveSeller(Seller seller);

    List<Seller> findSellers();

    Optional<Seller> findSellerById(Long id);
}
