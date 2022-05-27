package com.service.jmshop.service.impl;

import com.service.jmshop.repository.SellerRepository;
import com.service.jmshop.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
}
