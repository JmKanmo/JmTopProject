package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.domain.Seller;
import com.jmshop.jmshop_admin.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/register", produces = "application/json")
public class SellerController {
    private SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping(path = "/seller", consumes = "application/json")
    public ResponseEntity<Long> registerSeller(@RequestBody Seller seller) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.saveSeller(seller));
    }
}
