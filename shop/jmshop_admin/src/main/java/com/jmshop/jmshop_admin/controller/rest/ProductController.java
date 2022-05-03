package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Product;
import com.jmshop.jmshop_admin.service.ProductService;
import com.jmshop.jmshop_admin.util.JmUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(path = "/product")
    public ResponseEntity<Long> registerProduct(@Valid Product product, @RequestPart MultipartFile product_image) throws IOException {
        product.setImage(product_image.getBytes());
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveProduct(product));
    }
}
