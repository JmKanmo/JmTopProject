package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Delivery;
import com.jmshop.jmshop_admin.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping(path = "/delivery")
    public ResponseEntity<Long> registerDelivery(@Valid Delivery delivery) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.saveDelivery(delivery));
    }
}
