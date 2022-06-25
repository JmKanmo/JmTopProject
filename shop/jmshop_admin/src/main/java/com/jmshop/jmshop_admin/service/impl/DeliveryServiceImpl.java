package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.domain.Delivery;
import com.jmshop.jmshop_admin.repository.DeliveryRepository;
import com.jmshop.jmshop_admin.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Override
    public Long saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery).getId();
    }
}
