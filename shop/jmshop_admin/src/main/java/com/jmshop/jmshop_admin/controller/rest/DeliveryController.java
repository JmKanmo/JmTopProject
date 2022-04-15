package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.domain.Delivery;
import com.jmshop.jmshop_admin.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
public class DeliveryController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping(path = "/delivery", consumes = "application/json")
    public ResponseEntity<Long> registerDelivery(@RequestBody  Delivery delivery) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.saveDelivery(delivery));
    }
}
