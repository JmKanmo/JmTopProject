package com.service.jmshop.dto;

import com.service.jmshop.domain.Product;
import com.service.jmshop.domain.ProductImage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductMainDto {
    private final Long id;

    private final String imgSrc;

    private final String name;

    private final String sellerName;

    private final Integer discount;

    private final Long discountedPrice;

    private final Long basicPrice;

    public static ProductMainDto fromEntity(Product product) {
        return ProductMainDto.builder()
                .id(product.getId())
                .imgSrc(getImgSrc(product.getProductImages()))
                .name(product.getName())
                .sellerName(product.getSeller().getCName())
                .discount(product.getDiscount())
                .discountedPrice(product.getPrice() - ((product.getPrice() * product.getDiscount()) / 100))
                .basicPrice(product.getPrice())
                .build();
    }

    private static String getImgSrc(List<ProductImage> productImages) {
        if (productImages.isEmpty()) {
            return "../image/default_thumbnail.gif";
        }

        ProductImage productImage = productImages.stream().findFirst().get();

        if (productImage.getUuid().equals("")) {
            return "../image/default_thumbnail.gif";
        }

        return productImage.getUuid();
    }
}
