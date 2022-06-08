package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.dto.domain.Product;
import com.jmshop.jmshop_admin.service.CategoryService;
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
import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SellerService sellerService;

    private final FtpUtil ftpUtil;

    @PostMapping(path = "/product")
    public ResponseEntity<Long> registerProduct(@Valid Product product,
                                                @RequestPart MultipartFile product_image,
                                                @RequestParam Map<String, String> paramMap) throws Exception {
        // ftp 파일 전송 + DB에 uuid 저장 => 어떻게 UUID 만들지 기힉
        String staticFileUUID = Util.getStaticFileUUID(product_image);
        product.setStaticFileUuid(staticFileUUID);
        ftpUtil.ftpFileUpload(staticFileUUID,product_image.getInputStream());
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
