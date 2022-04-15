package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.domain.Delivery;
import com.jmshop.jmshop_admin.repository.DeliveryRepository;
import com.jmshop.jmshop_admin.service.DeliveryService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Long saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery).getId();
    }
}
