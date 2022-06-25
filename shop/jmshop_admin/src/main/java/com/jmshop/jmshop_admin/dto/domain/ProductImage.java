package com.jmshop.jmshop_admin.dto.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "product_image")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "product")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductImage fromEntity(Product product, String imgSrc) {
        return ProductImage.builder()
                .uuid(imgSrc)
                .product(product)
                .build();
    }
}