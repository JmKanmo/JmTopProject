package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.domain.Seller;
import com.jmshop.jmshop_admin.repository.SellerRepository;
import com.jmshop.jmshop_admin.service.SellerService;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Long saveSeller(Seller seller) {
        return sellerRepository.save(seller).getId();
    }
}
