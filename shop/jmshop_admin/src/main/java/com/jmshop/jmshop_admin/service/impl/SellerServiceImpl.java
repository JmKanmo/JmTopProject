package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.Seller;
import com.jmshop.jmshop_admin.repository.SellerRepository;
import com.jmshop.jmshop_admin.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Override
    public Long saveSeller(Seller seller) {
        return sellerRepository.save(seller).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Seller> findSellers() {
        return sellerRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Seller> findSellerById(Long id) {
        return Optional.ofNullable(sellerRepository.findSellerById(id));
    }
}
