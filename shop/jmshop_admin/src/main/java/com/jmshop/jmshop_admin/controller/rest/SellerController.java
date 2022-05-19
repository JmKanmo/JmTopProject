package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Seller;
import com.jmshop.jmshop_admin.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(path = "/seller")
    public ResponseEntity<Long> registerSeller(@Valid Seller seller) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.saveSeller(seller));
    }

    @GetMapping(path = "/seller")
    public ResponseEntity<List<Seller>> getSellers() {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.findSellers());
    }
}
