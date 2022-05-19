package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Category;
import com.jmshop.jmshop_admin.dto.domain.Product;
import com.jmshop.jmshop_admin.service.CategoryService;
import com.jmshop.jmshop_admin.service.ProductService;
import com.jmshop.jmshop_admin.service.SellerService;
import com.jmshop.jmshop_admin.util.JmUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SellerService sellerService;

    @PostMapping(path = "/product")
    public ResponseEntity<Long> registerProduct(@Valid Product product,
                                                @RequestPart MultipartFile product_image,
                                                @RequestParam Map<String, String> paramMap) throws Exception {
        product.setImage(product_image.getBytes());

        product.setCategory(categoryService.findCategoryById(Long.parseLong(paramMap.get("categoryId"))).orElseThrow(() -> {
            throw new NullPointerException("JmShopAdmin [ProductController:registerProduct] While find product category, category ullPointerException occur");
        }));

        product.setSeller(sellerService.findSellerById(Long.parseLong(paramMap.get("sellerId"))).orElseThrow(() -> {
            throw new NullPointerException("JmShopAdmin [ProductController:registerProduct] While find product seller, seller NullPointerException occur");
        }));

        // TODO delivery find
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveProduct(product));
    }
}
