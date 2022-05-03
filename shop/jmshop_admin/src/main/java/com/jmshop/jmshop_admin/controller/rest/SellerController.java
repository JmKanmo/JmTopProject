package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Seller;
import com.jmshop.jmshop_admin.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(path = "/seller", consumes = "application/json")
    public ResponseEntity<Long> registerSeller(@Valid @RequestBody Seller seller) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.saveSeller(seller));
    }
}
