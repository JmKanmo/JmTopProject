package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Product;
import com.jmshop.jmshop_admin.dto.domain.ProductImage;
import com.jmshop.jmshop_admin.service.CategoryService;
import com.jmshop.jmshop_admin.service.ProductImageService;
import com.jmshop.jmshop_admin.service.ProductService;
import com.jmshop.jmshop_admin.service.SellerService;
import com.jmshop.jmshop_admin.util.FtpUtil;
import com.jmshop.jmshop_admin.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final SellerService sellerService;

    @PostMapping(path = "/product")
    public ResponseEntity<Long> registerProduct(@Valid Product product,
                                                @RequestParam Map<String, String> paramMap) {
        long result = 0L;
        String[] imgSrcList = paramMap.get("imgSrcList").split(",");

        product.setCategory(categoryService.findCategoryById(Long.parseLong(paramMap.get("categoryId"))).orElseThrow(() -> {
            throw new NullPointerException("JmShopAdmin [ProductController:registerProduct] While find product category, category ullPointerException occur");
        }));

        product.setSeller(sellerService.findSellerById(Long.parseLong(paramMap.get("sellerId"))).orElseThrow(() -> {
            throw new NullPointerException("JmShopAdmin [ProductController:registerProduct] While find product seller, seller NullPointerException occur");
        }));

        result += productService.saveProduct(product);

        for (String imgSrc : imgSrcList) {
            if (imgSrc == null || imgSrc.isEmpty()) {
                continue;
            }
            result += productImageService.insertProductImage(ProductImage.fromEntity(product, imgSrc));
        }

        // TODO set delivery find logic
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
