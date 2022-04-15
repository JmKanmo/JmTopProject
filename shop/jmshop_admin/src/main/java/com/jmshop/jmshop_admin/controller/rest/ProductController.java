package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.domain.*;
import com.jmshop.jmshop_admin.service.ProductService;
import com.jmshop.jmshop_admin.util.JmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(path = "/register")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/product")
    public ResponseEntity<Long> registerProduct(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        Product product = JmUtil.getProductFromMultiPartFile(multipartHttpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveProduct(product));
    }
}
